package com.javaee.fooddeliverysystem.entity;

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

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }
}