package com.javaee.fooddeliverysystem.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Permission {
    private int id;
    private String name;
    private String url;
    private int roleId;

    public Permission() {}

    public Permission(int id, String name, String url, int roleId) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.roleId = roleId;
    }

}