package com.javaee.fooddeliverysystem.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class Store {
    private int id;
    private String name;
    private String address;
    private BigDecimal score;
    private String status;

    public Store(){ }

    public Store(int id, String name, String address, BigDecimal score, String status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.score = score;
        this.status = status;
    }

}