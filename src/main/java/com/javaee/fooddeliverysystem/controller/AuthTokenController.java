package com.javaee.fooddeliverysystem.controller;

import com.javaee.fooddeliverysystem.entity.Account;
import com.javaee.fooddeliverysystem.entity.Role;
import com.javaee.fooddeliverysystem.security.JwtUtil;
import com.javaee.fooddeliverysystem.security.PasswordEncoderUtil;
import com.javaee.fooddeliverysystem.service.AccountService;
import com.javaee.fooddeliverysystem.service.RoleService;
import com.javaee.fooddeliverysystem.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthTokenController {

    @Autowired private AccountService accountService;
    @Autowired private RoleService roleService;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private TokenService tokenService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {
        Map<String, Object> result = new HashMap<>();
        String account = body.get("account");
        String password = body.get("password");
        Integer roleId = body.get("roleId") == null || body.get("roleId").isEmpty()
                ? null : Integer.valueOf(body.get("roleId"));

        Account user = accountService.selectAll().stream()
                .filter(a -> a.getAccount().equals(account))
                .findFirst().orElse(null);

        if (user == null || !PasswordEncoderUtil.matches(password, user.getPassword())) {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
            return result;
        }
        if ("冻结".equals(user.getStatus())) {
            result.put("success", false);
            result.put("message", "账号已被冻结");
            return result;
        }

        List<Role> roles = roleService.selectRolesByUserId(user.getId());
        Role chosen = null;
        for (Role r : roles) {
            if (roleId != null && r.getId() == roleId) { chosen = r; break; }
        }
        if (chosen == null) {
            result.put("success", false);
            result.put("message", "该账号未绑定所选角色");
            return result;
        }

        String token = jwtUtil.generate(user.getId(), user.getAccount(), chosen.getId());
        tokenService.saveOnline(user.getId(), token);

        result.put("success", true);
        result.put("token", token);
        result.put("userId", user.getId());
        result.put("roleId", chosen.getId());
        result.put("expireSeconds", jwtUtil.getExpireSeconds());
        return result;
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            tokenService.blacklist(token);
            try {
                Map<String, Object> claims = jwtUtil.parse(token);
                tokenService.removeOnline((Integer) claims.get("userId"));
            } catch (Exception ignored) {}
        }
        result.put("success", true);
        result.put("message", "已注销");
        return result;
    }
}