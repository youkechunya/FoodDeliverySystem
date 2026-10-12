package com.javaee.fooddeliverysystem.service.impl;

import com.javaee.fooddeliverysystem.entity.Role;
import com.javaee.fooddeliverysystem.mapper.RoleMapper;
import com.javaee.fooddeliverysystem.mapper.UserRoleMapper;
import com.javaee.fooddeliverysystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 使用“先删除、再批量插入”的方式保存账号角色。
     * 事务可以保证角色不会保存到一半。
     */
    @Override
    @Transactional
    public boolean updateUserRoles(Integer userId, List<Integer> roleIds) {
        userRoleMapper.deleteByUserId(userId);

        if (roleIds != null && !roleIds.isEmpty()) {
            userRoleMapper.insertBatch(userId, roleIds);
        }

        return true;
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