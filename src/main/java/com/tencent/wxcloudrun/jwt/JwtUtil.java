package com.tencent.wxcloudrun.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {


    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private long expire;

    @Value("${jwt.issuer}")
    private String issuer;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        try {
            // 使用确定性的密钥生成方法
            this.secretKey = generateDeterministicSecretKey(secret);
        } catch (Exception e) {
            throw new RuntimeException("JWT key initialization failed", e);
        }
    }

    /**
     * 确定性的密钥生成方法（确保跨环境一致性）
     */
    private SecretKey generateDeterministicSecretKey(String secret) {
        try {
            // 1. 使用UTF-8编码转换为字节数组
            byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);

            // 2. 使用SHA-256哈希确保固定长度
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(secretBytes);

            // 3. 使用Base64编码再次确保一致性
            String base64Hashed = Base64.getEncoder().encodeToString(hashedBytes);
            byte[] finalBytes = base64Hashed.getBytes(StandardCharsets.UTF_8);

            // 4. 取前32字节作为密钥
            byte[] keyBytes = new byte[32];
            System.arraycopy(finalBytes, 0, keyBytes, 0, Math.min(finalBytes.length, 32));

            // 5. 如果不足32字节，用确定性模式填充
            if (finalBytes.length < 32) {
                for (int i = finalBytes.length; i < 32; i++) {
                    keyBytes[i] = (byte) (i % 256);
                }
            }
            return new SecretKeySpec(keyBytes, "HmacSHA256");
        } catch (Exception e) {
            throw new RuntimeException("Deterministic secret key generation failed", e);
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
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * 获取密钥信息用于调试
     */
    public String getKeyInfo() {
        return bytesToHex(secretKey.getEncoded());
    }
}