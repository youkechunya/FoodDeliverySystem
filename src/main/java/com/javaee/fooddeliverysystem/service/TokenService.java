package com.javaee.fooddeliverysystem.service;

import com.javaee.fooddeliverysystem.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    private static final String BLACKLIST_PREFIX = "jwt:blacklist:";
    private static final String ONLINE_PREFIX = "jwt:online:";

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private JwtUtil jwtUtil;

    public void saveOnline(Integer userId, String token) {
        redis.opsForValue().set(
                ONLINE_PREFIX + userId,
                token,
                jwtUtil.getExpireSeconds(),
                TimeUnit.SECONDS
        );
    }

    public void blacklist(String token) {
        redis.opsForValue().set(
                BLACKLIST_PREFIX + token,
                "1",
                jwtUtil.getExpireSeconds(),
                TimeUnit.SECONDS
        );
    }

    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(redis.hasKey(BLACKLIST_PREFIX + token));
    }

    public void removeOnline(Integer userId) {
        redis.delete(ONLINE_PREFIX + userId);
    }
}