package com.javaee.fooddeliverysystem.service;

import com.javaee.fooddeliverysystem.entity.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> selectAll();
    List<Permission> selectByRoleId(Integer roleId);
    Permission selectById(Integer id);
    boolean insert(Permission permission);
    boolean update(Permission permission);
    boolean deleteById(Integer id);
}