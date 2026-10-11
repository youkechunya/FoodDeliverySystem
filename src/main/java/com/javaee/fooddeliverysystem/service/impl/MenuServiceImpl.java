package com.javaee.fooddeliverysystem.service.impl;

import com.javaee.fooddeliverysystem.entity.Menu;
import com.javaee.fooddeliverysystem.mapper.MenuMapper;
import com.javaee.fooddeliverysystem.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> selectByRoleId(Integer roleId) {
        return menuMapper.selectByRoleId(roleId);
    }
}