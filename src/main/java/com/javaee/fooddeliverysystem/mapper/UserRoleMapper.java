package com.javaee.fooddeliverysystem.mapper;

import com.javaee.fooddeliverysystem.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    /**
     * 查询账号拥有的所有角色
     */
    List<Role> selectRolesByUserId(@Param("userId") Integer userId);

    /**
     * 删除账号原有的全部角色
     */
    int deleteByUserId(@Param("userId") Integer userId);

    /**
     * 批量插入账号角色
     */
    int insertBatch(@Param("userId") Integer userId,@Param("roleIds") List<Integer> roleIds);

    /**
     * 分配单个角色
     */
    int insert(@Param("userId") Integer userId,@Param("roleId") Integer roleId);

    /**
     * 删除单个角色
     */
    int delete(@Param("userId") Integer userId,@Param("roleId") Integer roleId);
}