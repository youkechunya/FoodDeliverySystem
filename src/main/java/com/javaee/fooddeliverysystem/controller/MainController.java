package com.javaee.fooddeliverysystem.controller;

import com.javaee.fooddeliverysystem.entity.Account;
import com.javaee.fooddeliverysystem.entity.Role;
import com.javaee.fooddeliverysystem.service.MenuService;
import com.javaee.fooddeliverysystem.service.RoleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/main")
    public String main(HttpSession session, Model model) {
        Account user = (Account) session.getAttribute("loginUser");
        Role role = (Role) session.getAttribute("loginRole");
        if (user == null || role == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("menus", menuService.selectByRoleId(role.getId()));
        model.addAttribute("currentRole", role);
        return "main";
    }
}