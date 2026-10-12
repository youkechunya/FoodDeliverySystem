package com.javaee.fooddeliverysystem.security;

public final class UserContext {

    private static final ThreadLocal<Integer> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<Integer> ROLE_ID = new ThreadLocal<>();

    private UserContext() {}

    public static void set(Integer userId, Integer roleId) {
        USER_ID.set(userId);
        ROLE_ID.set(roleId);
    }

    public static Integer getUserId() { return USER_ID.get(); }
    public static Integer getRoleId() { return ROLE_ID.get(); }

    public static void clear() {
        USER_ID.remove();
        ROLE_ID.remove();
    }
}