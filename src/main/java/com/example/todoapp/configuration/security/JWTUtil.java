package com.example.todoapp.configuration.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.example.todoapp.dtos.auth.TokenResponse;
import com.example.todoapp.enums.TokenType;

import java.security.Key;
import java.util.Date;

import static com.example.todoapp.enums.TokenType.ACCESS;
import static com.example.todoapp.enums.TokenType.REFRESH;

@Service
public class JWTUtil {

    @Value("${jwt.access.token.secret.key}")
    private String ACCESS_TOKEN_SECRET_KEY;
    @Value("${jwt.refresh.token.secret.key}")
    public String REFRESH_TOKEN_SECRET_KEY;

    @Value("${jwt.access.token.expiry}")
    private Long accessTokenExpiryInMinutes;
    @Value("${jwt.refresh.token.expiry}")
    private Long refreshTokenExpiryInMinutes;

    public TokenResponse generateToken(@NonNull String username) {
        TokenResponse tokenResponse = new TokenResponse();
        generateAccessToken(username, tokenResponse);
        generateRefreshToken(username, tokenResponse);
        return tokenResponse;
    }

    public TokenResponse generateAccessToken(@NonNull String username, @NonNull TokenResponse tokenResponse) {
        tokenResponse.setAccessTokenExpiry(new Date(System.currentTimeMillis() + accessTokenExpiryInMinutes));
        String accessToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setIssuer("https://github.com/ElbekKholmatov")
                .setExpiration(tokenResponse.getAccessTokenExpiry())
                .signWith(signKey(ACCESS), SignatureAlgorithm.HS512)
                .compact();
        tokenResponse.setAccessToken(accessToken);
        return tokenResponse;
    }


    public void generateRefreshToken(@NonNull String username, @NonNull TokenResponse tokenResponse) {
        tokenResponse.setRefreshTokenExpiry(new Date(System.currentTimeMillis() + refreshTokenExpiryInMinutes));
        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setIssuer("https://www.linkedin.com/in/ElbekKholmatov")
                .setExpiration(tokenResponse.getRefreshTokenExpiry())
                .signWith(signKey(REFRESH), SignatureAlgorithm.HS256)
                .compact();
        tokenResponse.setRefreshToken(refreshToken);
    }

    public boolean isTokenValid(@NonNull String token, TokenType tokenType) {
        try {
            Claims claims = getClaims(token, tokenType);
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getUsername(@NonNull String token, TokenType tokenType) {
        Claims claims = getClaims(token, tokenType);
        return claims.getSubject();

    }


    private Claims getClaims(String token, TokenType tokenType) {
        return Jwts.parserBuilder()
                .setSigningKey(signKey(tokenType))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private @NotNull Key signKey(@NotNull TokenType tokenType) {
        byte[] bytes = Decoders.BASE64.decode(tokenType.equals(ACCESS) ? ACCESS_TOKEN_SECRET_KEY : REFRESH_TOKEN_SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }

    public Date getExpiry(String token, TokenType tokenType) {
        Claims claims = getClaims(token, tokenType);
        return claims.getExpiration();
    }

}
