package com.medhead.authservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(UserDetails userDetails) {
        long currentTimeMillis = System.currentTimeMillis();
        Date issuedAt = new Date(currentTimeMillis);
        Date expirationDate = new Date(currentTimeMillis + expiration * 1000);

        String header = Base64.getEncoder().encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes());
        String payload = Base64.getEncoder().encodeToString(
                String.format("{\"sub\":\"%s\",\"iat\":%d,\"exp\":%d}",
                                userDetails.getUsername(),
                                issuedAt.getTime() / 1000,
                                expirationDate.getTime() / 1000)
                        .getBytes());

        String content = header + "." + payload;
        String signature = computeSignature(content);

        return content + "." + signature;
    }

    private String computeSignature(String content) {
        byte[] contentBytes = (content + secret).getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(contentBytes);
    }

    public String extractUsername(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid token format");
        }
        String payload = new String(Base64.getDecoder().decode(parts[1]));
        // This is a very simplistic way to extract the username. In a real application,
        // you'd want to use a JSON parser here.
        return payload.split("\"sub\":\"")[1].split("\"")[0];
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid token format");
        }
        String payload = new String(Base64.getDecoder().decode(parts[1]));
        long expiration = Long.parseLong(payload.split("\"exp\":")[1].split("}")[0]);
        return new Date(expiration * 1000).before(new Date());
    }
}