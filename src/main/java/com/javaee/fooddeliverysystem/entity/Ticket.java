package com.javaee.fooddeliverysystem.entity;

import java.util.Date;

public class Ticket {
    private int id;
    private Date publishDate;
    private String address;
    private Integer storeId;
    private String status;
    private Integer deliveryGuyId;
    private String note;

    public Ticket() {}

    public Ticket(int id, Date publishDate, String address, Integer storeId, String status, Integer deliveryGuyId, String note) {
        this.id = id;
        this.publishDate = publishDate;
        this.address = address;
        this.storeId = storeId;
        this.status = status;
        this.deliveryGuyId = deliveryGuyId;
        this.note = note;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Date getPublishDate() { return publishDate; }
    public void setPublishDate(Date publishDate) { this.publishDate = publishDate; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Integer getStoreId() { return storeId; }
    public void setStoreId(Integer storeId) { this.storeId = storeId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getDeliveryGuyId() { return deliveryGuyId; }
    public void setDeliveryGuyId(Integer deliveryGuyId) { this.deliveryGuyId = deliveryGuyId; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
