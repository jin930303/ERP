package com.example.erp.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    private final SecretKey secretKey;
    private final long expiration;

    public JwtProvider(@Value("${jwt.secret}") String secret,@Value("${jwt.expiration}") long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    //토큰 생성
    public String generateToken(Long memberId, String role, Long tenantId){
        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .claim("role",role)
                .claim("tenantId",tenantId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(secretKey)
                .compact();
    }

    //클레임 추출
    public Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getMemberId(String token){
        return Long.parseLong(getClaims(token).getSubject());
    }

    public String getRole(String token){
        return getClaims(token).get("role", String.class);
    }

    public Long getTenantId(String token){
        return getClaims(token).get("tenantId", Long.class);
    }

    // 유효성 검증
    public boolean validateToken(String token){
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }
}
