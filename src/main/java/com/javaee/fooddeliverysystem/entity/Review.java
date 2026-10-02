package com.javaee.fooddeliverysystem.entity;

public class Review {
    private int id;
    private Integer userId;
    private Integer storeId;
    private String content;

    public Review() {}

    public Review(int id, Integer userId, Integer storeId, String content) {
        this.id = id;
        this.userId = userId;
        this.storeId = storeId;
        this.content = content;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getStoreId() { return storeId; }
    public void setStoreId(Integer storeId) { this.storeId = storeId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
