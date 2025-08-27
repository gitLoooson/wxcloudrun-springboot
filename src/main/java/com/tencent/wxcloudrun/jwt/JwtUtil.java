package com.tencent.wxcloudrun.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private long expire;

    @Value("${jwt.issuer}")
    private String issuer;

    private SecretKey secretKey;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        try {
            // 生成确定性密钥
            this.secretKey = generateDeterministicSecretKey(secret);

            // 只初始化一次 parser，线程安全 & 高性能
            this.jwtParser = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("JWT initialization failed", e);
        }
    }

    /**
     * 确定性密钥生成：SHA-256(secret) → 前32字节
     */
    private SecretKey generateDeterministicSecretKey(String secret) {
        try {
            byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(secretBytes);

            // 直接取前32字节作为 HmacSHA256 密钥
            byte[] keyBytes = Arrays.copyOf(hashedBytes, 32);
            return new SecretKeySpec(keyBytes, "HmacSHA256");
        } catch (Exception e) {
            throw new RuntimeException("Secret key generation failed", e);
        }
    }

    public String generateToken(String openid, Long id) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expire * 1000);

        Map<String, Object> claims = new HashMap<>();
        claims.put("openid", openid);
        claims.put("userId", id);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .setIssuer(issuer)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String getOpenidFromToken(String token) {
        return getClaimsFromToken(token).get("openid", String.class);
    }

    public Long getUserIdFromToken(String token) {
        return getClaimsFromToken(token).get("userId", Long.class);
    }

    public boolean validateToken(String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (JwtException e) {
            // 可以换成日志框架：log.warn("Invalid token", e);
            System.out.println("Invalid token: " + e.getMessage());
            return false;
        }
    }

    /**
     * 获取密钥信息（调试用）
     */
    public String getKeyInfo() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
}
