package com.javaee.fooddeliverysystem.entity.dto;

import java.util.ArrayList;
import java.util.List;

public class AccountRoleDTO {
    private Integer userId;
    private List<Integer> roleIds = new ArrayList<>();

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}