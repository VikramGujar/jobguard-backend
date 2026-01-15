package com.jobguard.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.access-token-expiration-minutes}")
    private long accessTokenExpiryMinutes;

    public String generateAccessToken(String subject, String role) {
        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(subject)
                    .claim("role", role)
                    .issuer("jobguard-auth")
                    .issueTime(Date.from(Instant.now()))
                    .expirationTime(Date.from(Instant.now().plusSeconds(accessTokenExpiryMinutes * 60)))
                    .jwtID(UUID.randomUUID().toString())
                    .build();

            SignedJWT signedJWT = new SignedJWT(header, claims);
            JWSSigner signer = new MACSigner(secret.getBytes());
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create token", ex);
        }
    }

    public boolean validateToken(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(secret.getBytes());
            if (!jwt.verify(verifier)) return false;
            Date exp = jwt.getJWTClaimsSet().getExpirationTime();
            return exp.after(new Date());
        } catch (Exception ex) {
            return false;
        }
    }

    public String getSubject(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            return jwt.getJWTClaimsSet().getSubject();
        } catch (Exception ex) {
            return null;
        }
    }

    public String getRole(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            return (String) jwt.getJWTClaimsSet().getClaim("role");
        } catch (Exception ex) {
            return null;
        }
    }
}
