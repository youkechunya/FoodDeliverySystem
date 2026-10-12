package com.javaee.fooddeliverysystem.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class Ticket {
    private int id;
    private Date publishDate;
    private String address;
    private Integer storeId;
    private String status;
    private Integer deliveryGuyId;
    private String note;
    private Integer userId;
    private BigDecimal deliveryRate;

    public Ticket() {}

    public Ticket(int id, Date publishDate, String address, Integer storeId, String status,
                  Integer deliveryGuyId, String note, Integer userId) {
        this.id = id;
        this.publishDate = publishDate;
        this.address = address;
        this.storeId = storeId;
        this.status = status;
        this.deliveryGuyId = deliveryGuyId;
        this.note = note;
        this.userId = userId;
    }

}