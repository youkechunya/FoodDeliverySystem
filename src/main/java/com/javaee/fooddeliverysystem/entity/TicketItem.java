package com.javaee.fooddeliverysystem.entity;

import java.math.BigDecimal;

public class TicketItem {
    private int id;
    private Integer ticketId;
    private Integer itemId;
    private Integer quantity;
    private BigDecimal price;

    public TicketItem() {}

    public TicketItem(int id, Integer ticketId, Integer itemId, Integer quantity, BigDecimal price) {
        this.id = id;
        this.ticketId = ticketId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Integer getTicketId() { return ticketId; }
    public void setTicketId(Integer ticketId) { this.ticketId = ticketId; }
    public Integer getItemId() { return itemId; }
    public void setItemId(Integer itemId) { this.itemId = itemId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}
