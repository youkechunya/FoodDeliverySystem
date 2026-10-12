package com.javaee.fooddeliverysystem.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemType {
    private int id;
    private String type;

    public ItemType() {}

    public ItemType(int id, String type) {
        this.id = id;
        this.type = type;
    }

}
