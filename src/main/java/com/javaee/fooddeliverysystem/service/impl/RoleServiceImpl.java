package com.javaee.fooddeliverysystem.service.impl;

import com.javaee.fooddeliverysystem.entity.Role;
import com.javaee.fooddeliverysystem.mapper.RoleMapper;
import com.javaee.fooddeliverysystem.mapper.UserRoleMapper;
import com.javaee.fooddeliverysystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

    @Override
    public Role selectById(Integer id) {
        return roleMapper.selectById(id);
    }

    @Override
    public List<Role> selectRolesByUserId(Integer userId) {
        return userRoleMapper.selectRolesByUserId(userId);
    }

    @Override
    public boolean insert(Role role) {
        return roleMapper.insert(role) > 0;
    }

    @Override
    public boolean update(Role role) {
        return roleMapper.update(role) > 0;
    }

    @Override
    public boolean deleteById(Integer id) {
        return roleMapper.deleteById(id) > 0;
    }
}