package com.javaee.fooddeliverysystem.controller;

import com.javaee.fooddeliverysystem.entity.Account;
import com.javaee.fooddeliverysystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

/// 登录注册
@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/login")
    public String toLogin(HttpSession session) {
        if (session.getAttribute("loginUser") != null) {
            return "redirect:/main";
        }
        return "login";
    }

    @GetMapping("/register")
    public String toRegister() {
        return "register";
    }

    @PostMapping("/login")
    public String login(@RequestParam String account,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        for (Account a : accountService.selectAll()) {
            if (a.getAccount().equals(account) && a.getPassword().equals(password)) {
                session.setAttribute("loginUser", a);
                return "redirect:/main";
            }
        }
        model.addAttribute("error", "用户名或密码错误！");
        return "login";
    }

    @PostMapping("/register")
    public String register(@RequestParam String account,
                           @RequestParam String password,
                           Model model) {
        Account user = new Account();
        user.setAccount(account);
        user.setPassword(password);
        user.setStatus("正常");

        if (accountService.insert(user)) {
            model.addAttribute("success", "注册成功，请登录！");
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
        return "redirect:/auth/login";
    }

    @GetMapping("/api/currentUser")
    @ResponseBody
    public Account getCurrentUser(HttpSession session) {
        return (Account) session.getAttribute("loginUser");
    }
}