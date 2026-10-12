package com.javaee.fooddeliverysystem.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Report {
    private int id;
    private int reviewId;
    private int storeId;
    private int reporterId;
    private String reason;
    private String status;
    private Date createTime;

    // 非表字段，方便前端展示
    private String reviewContent;
    private String storeName;

    public Report() {}
}