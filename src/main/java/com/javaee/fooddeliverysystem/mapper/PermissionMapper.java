package com.javaee.fooddeliverysystem.mapper;

import com.javaee.fooddeliverysystem.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper {
    List<Permission> selectAll();
    List<Permission> selectByRoleId(@Param("roleId") Integer roleId);
    Permission selectById(@Param("id") Integer id);
    int insert(Permission permission);
    int update(Permission permission);
    int deleteById(@Param("id") Integer id);
}