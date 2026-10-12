package com.javaee.fooddeliverysystem.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret:FoodDeliverySystem-Secret-Key-Change-Me-2026-Please-32Bytes}")
    private String secret;

    @Value("${jwt.expire-minutes:120}")
    private long expireMinutes;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generate(Integer userId, String account, Integer roleId) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expireMinutes * 60_000);
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("account", account)
                .claim("roleId", roleId)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Map<String, Object> parse(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        Map<String, Object> map = new HashMap<>();
        map.put("userId", Integer.valueOf(claims.getSubject()));
        map.put("account", claims.get("account"));
        map.put("roleId", claims.get("roleId", Integer.class));
        return map;
    }

    public long getExpireSeconds() {
        return expireMinutes * 60;
    }
}