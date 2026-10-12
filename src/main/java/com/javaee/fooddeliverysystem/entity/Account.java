package com.javaee.fooddeliverysystem.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

}
