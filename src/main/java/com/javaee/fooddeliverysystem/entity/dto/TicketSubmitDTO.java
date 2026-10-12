package com.javaee.fooddeliverysystem.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TicketSubmitDTO {
    private String address;
    private String note;
    private Integer storeId;
    private List<ItemEntry> items;

    @Setter
    @Getter
    public static class ItemEntry {
        private Integer itemId;
        private Integer quantity;
        private java.math.BigDecimal price;

    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Integer getStoreId() { return storeId; }
    public void setStoreId(Integer storeId) { this.storeId = storeId; }
    public List<ItemEntry> getItems() { return items; }
    public void setItems(List<ItemEntry> items) { this.items = items; }
}