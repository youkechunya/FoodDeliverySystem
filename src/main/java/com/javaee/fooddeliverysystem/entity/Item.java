package com.javaee.fooddeliverysystem.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class Item {
    private int id;
    private String name;
    private Integer typeId;
    private BigDecimal price;
    private Integer sales;
    private Integer remain;
    private String note;

    public Item() {}

    public Item(int id, String name, Integer typeId, BigDecimal price, Integer sales, Integer remain, String note) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.price = price;
        this.sales = sales;
        this.remain = remain;
        this.note = note;
    }

}
