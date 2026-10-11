package com.javaee.fooddeliverysystem.service;

import com.javaee.fooddeliverysystem.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {
    List<Role> selectAll();
    Role selectById(@Param("id") Integer id);
    List<Role> selectRolesByUserId(@Param("userId") Integer userId);
    boolean insert(Role role);
    boolean update(Role role);
    boolean deleteById(@Param("id") Integer id);
}