package com.example.entity;

public class Customer extends Person {
    private String customerId;
    private String tier;

    public Customer() {}
    public Customer(String name, int age, String customerId, String tier) {
        super(name, age);
        this.customerId = customerId;
        this.tier = tier;
    }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getTier() { return tier; }
    public void setTier(String tier) { this.tier = tier; }
}

