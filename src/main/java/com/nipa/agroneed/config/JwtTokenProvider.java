package com.nipa.agroneed.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Configuration
public class JwtTokenProvider {

    private static final String SECRET_KEY = "nipa-key_nipa-key_nipa-key_nipa-key"; // Must be 32+ bytes
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public boolean isValidateToken(String jwtToken, HttpServletRequest request) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key) // Use Key object instead of String
                    .build()
                    .parseClaimsJws(jwtToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserIdFromToken(String jwtToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // Fixed signing key usage
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
        return Long.valueOf(claims.getSubject());
    }

    public String generateToken(Authentication authentication, HttpServletRequest request) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .claim("phone", userPrincipal.getUsername()) // Payload claim
                .setSubject(String.valueOf(userPrincipal.getId()))
                .setIssuedAt(now)
                .setExpiration(getExpirationTime(5L)) // 5-hour expiration
                .signWith(key, SignatureAlgorithm.HS256) // Use Key object, HS256
                .compact();
    }

    public static Date getExpirationTime(Long expireHours) {
        return new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(expireHours));
    }
}
