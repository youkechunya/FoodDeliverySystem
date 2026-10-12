package com.javaee.fooddeliverysystem.controller;

import com.javaee.fooddeliverysystem.entity.Account;
import com.javaee.fooddeliverysystem.entity.Role;
import com.javaee.fooddeliverysystem.mapper.UserRoleMapper;
import com.javaee.fooddeliverysystem.service.AccountService;
import com.javaee.fooddeliverysystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @GetMapping("/login")
    public String toLogin(HttpSession session, Model model) {
        if (session.getAttribute("loginUser") != null) {
            return "redirect:/main";
        }
        model.addAttribute("roles", roleService.selectAll());
        return "login";
    }

    @GetMapping("/register")
    public String toRegister() {
        return "register";
    }

    @PostMapping("/login")
    public String login(@RequestParam String account,
                        @RequestParam String password,
                        @RequestParam Integer roleId,
                        HttpSession session,
                        Model model) {

        Account user = null;
        for (Account a : accountService.selectAll()) {
            if (a.getAccount().equals(account)) {
                user = a;
                break;
            }
        }
        if (user == null || !com.javaee.fooddeliverysystem.security.PasswordEncoderUtil
                .matches(password, user.getPassword())) {
            model.addAttribute("error", "用户名或密码错误！");
            model.addAttribute("roles", roleService.selectAll());
            return "login";
        }

        List<Role> owned = roleService.selectRolesByUserId(user.getId());
        Role selected = null;
        for (Role r : owned) {
            if (r.getId() == roleId) {
                selected = r;
                break;
            }
        }
        if (selected == null) {
            model.addAttribute("error", "你没有权限登录该角色！");
            model.addAttribute("roles", roleService.selectAll());
            return "login";
        }

        if ("冻结".equals(user.getStatus())) {
            model.addAttribute("error", "该账号已被冻结！");
            model.addAttribute("roles", roleService.selectAll());
            return "login";
        }

        session.setAttribute("loginUser", user);
        session.setAttribute("loginRole", selected);
        return "redirect:/main";
    }

    @PostMapping("/register")
    public String register(@RequestParam String account,
                           @RequestParam String password,
                           Model model) {
        Account user = new Account();
        user.setAccount(account);
        user.setPassword(com.javaee.fooddeliverysystem.security.PasswordEncoderUtil.encode(password));
        user.setStatus("正常");

        if (accountService.insert(user)) {
            Integer customerRoleId = null;
            for (Role r : roleService.selectAll()) {
                if ("顾客".equals(r.getRoleName())) {
                    customerRoleId = r.getId();
                    break;
                }
            }
            if (customerRoleId == null) {
                customerRoleId = 5;
            }

            try {
                userRoleMapper.insert(user.getId(), customerRoleId);
            } catch (Exception e) {
                System.out.println("默认角色分配失败：" + e.getMessage());
            }

            model.addAttribute("success", "注册成功，请登录！");
            model.addAttribute("roles", roleService.selectAll());
            return "login";
        } else {
            model.addAttribute("error", "注册失败，请重试！");
            return "register";
        }
    }

    @GetMapping("/checkUsername")
    @ResponseBody
    public String checkUsername(@RequestParam String account) {
        for (Account a : accountService.selectAll()) {
            if (a.getAccount().equals(account)) {
                return "exist";
            }
        }
        return "available";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginUser");
        session.removeAttribute("loginRole");
        return "redirect:/auth/login";
    }

    @GetMapping("/api/currentUser")
    @ResponseBody
    public Account getCurrentUser(HttpSession session) {
        return (Account) session.getAttribute("loginUser");
    }

    @GetMapping("/roles")
    @ResponseBody
    public List<Role> rolesByAccount(@RequestParam String account) {
        for (Account a : accountService.selectAll()) {
            if (a.getAccount().equals(account)) {
                return roleService.selectRolesByUserId(a.getId());
            }
        }
        return new ArrayList<>();
    }

    @GetMapping("/roles/list")
    @ResponseBody
    public List<Role> allRoles() {
        return roleService.selectAll();
    }
}