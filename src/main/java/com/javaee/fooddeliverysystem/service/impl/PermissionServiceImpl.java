package com.javaee.fooddeliverysystem.service.impl;

import com.javaee.fooddeliverysystem.entity.Permission;
import com.javaee.fooddeliverysystem.mapper.PermissionMapper;
import com.javaee.fooddeliverysystem.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> selectAll() { return permissionMapper.selectAll(); }

    @Override
    public List<Permission> selectByRoleId(Integer roleId) { return permissionMapper.selectByRoleId(roleId); }

    @Override
    public Permission selectById(Integer id) { return permissionMapper.selectById(id); }

    @Override
    public boolean insert(Permission permission) { return permissionMapper.insert(permission) > 0; }

    @Override
    public boolean update(Permission permission) { return permissionMapper.update(permission) > 0; }

    @Override
    public boolean deleteById(Integer id) { return permissionMapper.deleteById(id) > 0; }
}