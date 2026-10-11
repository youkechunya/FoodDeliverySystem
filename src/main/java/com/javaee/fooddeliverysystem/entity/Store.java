package com.javaee.fooddeliverysystem.entity;

import java.math.BigDecimal;

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

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}