package com.javaee.fooddeliverysystem.service;

import com.javaee.fooddeliverysystem.entity.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> selectByRoleId(Integer roleId);
}