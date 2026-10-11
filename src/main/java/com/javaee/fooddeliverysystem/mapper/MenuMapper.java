package com.javaee.fooddeliverysystem.mapper;

import com.javaee.fooddeliverysystem.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<Menu> selectByRoleId(@Param("roleId") Integer roleId);
}