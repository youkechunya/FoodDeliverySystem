package com.javaee.fooddeliverysystem.entity;

import java.math.BigDecimal;

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

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getTypeId() { return typeId; }
    public void setTypeId(Integer typeId) { this.typeId = typeId; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getSales() { return sales; }
    public void setSales(Integer sales) { this.sales = sales; }
    public Integer getRemain() { return remain; }
    public void setRemain(Integer remain) { this.remain = remain; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
