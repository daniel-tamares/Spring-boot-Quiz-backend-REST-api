package com.example.demo.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    public enum RefreshTokenStatus {
        VALID,
        REUSED,
        TAMPERED,
        EXPIRED
    }

    @Value("${app.security.jwt.secret-key}")
    private String secretKey;

    @Value("${app.security.jwt.access-token-expiration}")
    private Duration accessTokenExpiration;

    @Value("${app.security.jwt.refresh-token-expiration}")
    private Duration refreshTokenExpiration;

    @Value("${app.security.jwt.issuer}")
    private String issuer;

    private SecretKey getSigningKey()
    {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(UserDetails userDetails)
    {
        Instant now = Instant.now();
        Instant expiryAccessToken = now.plus(accessTokenExpiration);

        return Jwts.builder()
                .issuer(issuer)
                .claim("roles", userDetails.getAuthorities()
                        .stream()
                        .map(a -> a.getAuthority())
                        .collect(Collectors.toSet()))
                .claim("token_type", "access")
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiryAccessToken))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails, String jti, String familyId)
    {
        Instant now = Instant.now();
        Instant expiryDate = now.plus(refreshTokenExpiration);

        return Jwts.builder()
                .issuer(issuer)
                .claim("roles", userDetails.getAuthorities()
                        .stream()
                        .map(a -> a.getAuthority())
                        .collect(Collectors.toSet()))
                .claim("token_type", "refresh")
                .claim("jti", jti)
                .claim("familyId", familyId)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiryDate))
                .signWith(getSigningKey())
                .compact();
    }

    public Claims parseClaims(String token)
    {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .clockSkewSeconds(60)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateAccessToken(String accessToken)
    {
        try {
            Claims claims = parseClaims(accessToken);

            if (!issuer.equals(claims.getIssuer()))
            {
                return  false;
            }

            if (!"access".equals(claims.get("token_type", String.class)))
            {
                return false;
            }
            if (claims.getExpiration().before(new Date()))
            {
                logger.warn("expird token");
                return false;
            }
            return true;

        } catch (JwtException e)
        {
            logger.warn("Invalid accesss token {}", e.getMessage());
            return false;
        }
    }

    public boolean validateRefreshToken(String refreshToken)
    {
        try {
            Claims claims = parseClaims(refreshToken);

            if (!issuer.equals(claims.getIssuer()))
            {
                return false;
            }

            if (!"refresh".equals(claims.get("token_type", String.class)))
            {
                return false;
            }
            if (claims.getExpiration().before(new Date()))
            {
                return false;
            }

            return true;

        } catch (JwtException e) {
            logger.warn("Invalid refresh token: {}", e.getMessage());
            return false;
        }
    }

    public String generateJti() {
        return UUID.randomUUID().toString();
    }

    public String generateFamilyId() {
        return UUID.randomUUID().toString();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public String extractTokenType(String token) {
        return parseClaims(token).get("token_type", String.class);
    }

    public String extractJti(String token) {
        return parseClaims(token).get("jti", String.class);
    }

    public String extractFamilyId(String token) {
        return parseClaims(token).get("familyId", String.class);
    }

    public Instant extractRefreshTokenExpiration(String refreshToken) {
        Claims claims = parseClaims(refreshToken);

        String tokenType = claims.get("token_type", String.class);
        if (!"refresh".equals(tokenType)) {
            throw new JwtException("Token is not a refresh token");
        }

        return claims.getExpiration().toInstant();
    }

    public String validateRefreshTokenStatus(String refreshToken) {
        try {
            // 1️⃣ Parse claims
            Claims claims = parseClaims(refreshToken);

            // 2️⃣ Check issuer
            if (!issuer.equals(claims.getIssuer())) {
                return "tampered";
            }

            // 3️⃣ Check token type
            String tokenType = claims.get("token_type", String.class);
            if (!"refresh".equals(tokenType)) {
                return "tampered";
            }

            // 4️⃣ Check expiration
            if (claims.getExpiration().before(new Date())) {
                return "expired";
            }

            // 6️⃣ If all checks pass
            return "valid";

        } catch (ExpiredJwtException e) {
            return "expired";
        } catch (JwtException e) {
            return "tampered";
        }
    }


}













