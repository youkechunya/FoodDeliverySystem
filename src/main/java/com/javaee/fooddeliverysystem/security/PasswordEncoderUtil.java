package com.javaee.fooddeliverysystem.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class PasswordEncoderUtil {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private PasswordEncoderUtil() {}

    public static String encode(String raw) {
        return ENCODER.encode(raw);
    }

    /**
     * 兼容旧明文数据：存储值以 $2a$/$2b$/$2y$ 开头走 BCrypt，
     * 否则按明文比较（历史数据过渡用）。
     */
    public static boolean matches(String raw, String stored) {
        if (raw == null || stored == null) return false;
        if (stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$")) {
            return ENCODER.matches(raw, stored);
        }
        return raw.equals(stored);
    }
}