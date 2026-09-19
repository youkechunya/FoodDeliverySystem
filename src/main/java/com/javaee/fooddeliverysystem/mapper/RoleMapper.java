package com.javaee.fooddeliverysystem.mapper;

import java.util.List;
import com.javaee.fooddeliverysystem.entity.Role;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    /// 选中所有
    List<Role> selectAll();
    /// 通过 ID 选择
    Role selectById(@Param("id") Integer id);
    /// 新增角色
    int insert(Role role);
    /// 更新角色
    int update(Role role);
    /// 通过 ID 删除角色
    int deleteById(@Param("id") Integer id);
}
