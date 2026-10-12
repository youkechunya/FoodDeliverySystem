package com.javaee.fooddeliverysystem.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

}
