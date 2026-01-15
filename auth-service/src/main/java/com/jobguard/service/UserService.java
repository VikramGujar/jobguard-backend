package com.jobguard.service;

import com.jobguard.dto.*;
import com.jobguard.model.*;
import com.jobguard.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final RefreshTokenRepository refreshTokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final long refreshTokenExpiryDays;

    public UserService(UserRepository userRepo, RefreshTokenRepository refreshTokenRepo,
                       PasswordEncoder passwordEncoder, JwtService jwtService,
                       @org.springframework.beans.factory.annotation.Value("${security.jwt.refresh-token-expiration-days}") long refreshTokenExpiryDays) {
        this.userRepo = userRepo;
        this.refreshTokenRepo = refreshTokenRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenExpiryDays = refreshTokenExpiryDays;
    }

    @Transactional
    public void register(RegisterRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        User u = User.builder()
                .email(req.getEmail())
                .username(req.getUsername())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .role(Role.ROLE_USER)
                .build();
        userRepo.save(u);
    }

    public LoginResponse login(LoginRequest req) {
        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash()))
            throw new RuntimeException("Invalid credentials");

        String access = jwtService.generateAccessToken(user.getEmail(), user.getRole().name());
        String refresh = createRefreshToken(user);
        long expiresIn = 60L * 15; // 15 minutes as seconds - keep in sync with config
        return new LoginResponse(access, refresh, expiresIn);
    }

    private String createRefreshToken(User user) {
        String token = UUID.randomUUID().toString();
        RefreshToken rt = RefreshToken.builder()
                .token(token)
                .user(user)
                .expiryDate(Instant.now().plusSeconds(refreshTokenExpiryDays * 24 * 3600))
                .build();
        refreshTokenRepo.save(rt);
        return token;
    }

    public LoginResponse refreshToken(String refreshToken) {
        RefreshToken rt = refreshTokenRepo.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        if (rt.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepo.delete(rt);
            throw new RuntimeException("Refresh token expired");
        }
        User user = rt.getUser();
        String access = jwtService.generateAccessToken(user.getEmail(), user.getRole().name());
        String newRefresh = createRefreshToken(user); // rotate
        refreshTokenRepo.delete(rt); // revoke old
        return new LoginResponse(access, newRefresh, 60L * 15);
    }

    public void logout(String refreshToken) {
        refreshTokenRepo.findByToken(refreshToken).ifPresent(refreshTokenRepo::delete);
    }

    public User getUserByEmail(String email){
        return userRepo.findByEmail(email).orElse(null);
    }
}
