package com.jobguard.controller;

import com.jobguard.dto.*;
import com.jobguard.service.UserService;
import com.jobguard.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    public AuthController(UserService userService) { this.userService = userService; }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        userService.register(req);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(userService.login(req));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@Valid @RequestBody RefreshRequest req) {
        return ResponseEntity.ok(userService.refreshToken(req.getRefreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody RefreshRequest req) {
        userService.logout(req.getRefreshToken());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<User> me(Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(401).build();
        String email = (String) authentication.getPrincipal();
        User u = userService.getUserByEmail(email);
        if (u == null) return ResponseEntity.notFound().build();
        u.setPasswordHash(null);
        return ResponseEntity.ok(u);
    }
}
