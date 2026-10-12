package com.javaee.fooddeliverysystem.security;

import com.javaee.fooddeliverysystem.service.TokenService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Order(1)
public class JwtAuthFilter implements Filter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenService tokenService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                if (tokenService.isBlacklisted(token)) {
                    writeUnauthorized(response, "token 已失效");
                    return;
                }
                Map<String, Object> claims = jwtUtil.parse(token);
                UserContext.set((Integer) claims.get("userId"), (Integer) claims.get("roleId"));
            } catch (Exception e) {
                writeUnauthorized(response, "token 无效或已过期");
                return;
            }
        }

        try {
            chain.doFilter(req, res);
        } finally {
            UserContext.clear();
        }
    }

    private void writeUnauthorized(HttpServletResponse response, String msg) throws IOException {
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"success\":false,\"message\":\"" + msg + "\"}");
    }
}