package com.tencent.wxcloudrun.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;  // Base64 编码的 key，至少 64 字节

    @Value("${jwt.expire}")
    private long expire;

    @Value("${jwt.issuer}")
    private String issuer;

    private SecretKey secretKey;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        // 从 Base64 解码生成 SecretKey
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);

        // 初始化 parser，线程安全
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
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
            System.out.println("Invalid token: " + e.getMessage());
            return false;
        }
    }

    /**
     * 调试用：打印 SecretKey Base64
     */
    public String getKeyInfo() {
        return io.jsonwebtoken.io.Encoders.BASE64.encode(secretKey.getEncoded());
    }
}
