package com.javaee.fooddeliverysystem.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Complaint {
    private int id;
    private int userId;
    private int storeId;
    private Integer ticketId;
    private String content;
    private String status;
    private String reply;
    private Date createTime;

    public Complaint() {}

}