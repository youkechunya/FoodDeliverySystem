package com.javaee.fooddeliverysystem.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
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

}