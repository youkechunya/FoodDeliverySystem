package com.javaee.fooddeliverysystem.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonalInformation {
    private int id;
    private int accountId;
    private String address;

    public PersonalInformation() { }

    public PersonalInformation(int id, int accountId, String address) {
        this.id = id;
        this.accountId = accountId;
        this.address = address;
    }

}
