package com.javaee.fooddeliverysystem.entity;

public class Account {
    private int id;
    private String account;
    private String password;
    private String status;

    public Account() {}

    public Account(int id, String account, String password, String status) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
