package com.jobguard.config;

import com.jobguard.service.JwtService;
import com.jobguard.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
<<<<<<< HEAD
import org.springframework.stereotype.Component;
=======
>>>>>>> e1f4ccb1e40dfea59f86f64f86d7a3954ee3a267
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

<<<<<<< HEAD
@Component
=======
>>>>>>> e1f4ccb1e40dfea59f86f64f86d7a3954ee3a267
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
<<<<<<< HEAD
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getServletPath();

        // ⛔ Skip JWT validation for public endpoints
        if (path.startsWith("/api/auth/")
                || path.startsWith("/actuator/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // ⬇ existing JWT logic continues here
    }

=======
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtService.validateToken(token)) {
                String subject = jwtService.getSubject(token); // subject = user email
                String role = jwtService.getRole(token);
                var auth = new UsernamePasswordAuthenticationToken(subject, null,
                        List.of(new SimpleGrantedAuthority(role)));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
>>>>>>> e1f4ccb1e40dfea59f86f64f86d7a3954ee3a267
}
