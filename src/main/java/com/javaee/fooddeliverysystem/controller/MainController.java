package com.javaee.fooddeliverysystem.controller;

import com.javaee.fooddeliverysystem.entity.Account;
import com.javaee.fooddeliverysystem.entity.Menu;
import com.javaee.fooddeliverysystem.entity.Role;
import com.javaee.fooddeliverysystem.service.MenuService;
import com.javaee.fooddeliverysystem.service.RoleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/main")
    public String main(HttpSession session, Model model) {
        Account user = (Account) session.getAttribute("loginUser");
        if (user == null) {
            return "redirect:/auth/login";
        }
        List<Role> roles = roleService.selectRolesByUserId(user.getId());
        List<Menu> menus = new ArrayList<>();
        if (roles != null && !roles.isEmpty()) {
            menus = menuService.selectByRoleId(roles.get(0).getId());
        }
        model.addAttribute("menus", menus);
        return "main";
    }
}