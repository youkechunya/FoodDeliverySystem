package com.javaee.fooddeliverysystem.entity;

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

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
