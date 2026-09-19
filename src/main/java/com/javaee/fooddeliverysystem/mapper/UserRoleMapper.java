package com.javaee.fooddeliverysystem.mapper;

import com.javaee.fooddeliverysystem.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper {
    /// 通过 ID 查询角色
    List<Role> selectRolesByUserId(@Param("userId") Integer userId);
    /// 分配角色
    int insert(@Param("userId") Integer userId,@Param("roleId") Integer roleId);
    /// 删除角色
    int delete(@Param("userId") Integer userId,@Param("roleId") Integer roleId);
}
