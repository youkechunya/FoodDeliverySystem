package com.javaee.fooddeliverysystem.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class StoreOwner {
    private int id;
    private int accountId;
    private int storeId;
    private Date createTime;

    public StoreOwner() {}

}