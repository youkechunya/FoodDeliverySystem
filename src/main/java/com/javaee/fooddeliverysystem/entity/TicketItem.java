package com.javaee.fooddeliverysystem.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class TicketItem {
    private int id;
    private Integer ticketId;
    private Integer itemId;
    private Integer quantity;
    private BigDecimal price;
    private String itemName;

    public TicketItem() {}
}