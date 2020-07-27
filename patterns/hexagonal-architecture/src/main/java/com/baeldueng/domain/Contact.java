package com.baeldueng.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class Contact {
    private String name;
    private String mobileNumber;
    private String id;

    public Contact(String name, String mobileNumber) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.id = UUID.randomUUID().toString();
    }
}
