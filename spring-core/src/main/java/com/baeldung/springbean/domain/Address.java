package com.baeldung.springbean.domain;

import lombok.Data;

@Data
public class Address {
    private String street;
    private int number;

    public Address(String street, int number) {
        this.street = street;
        this.number = number;
    }
}
