package com.javaee.fooddeliverysystem.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Role {
    private int id;
    private String roleName;

    public Role(){ }

    public Role(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

}
