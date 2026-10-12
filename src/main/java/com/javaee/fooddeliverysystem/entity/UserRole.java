package com.javaee.fooddeliverysystem.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRole {
    private int userId;
    private int roleId;

    public UserRole(){ }

    public UserRole(int userId, int roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

}
