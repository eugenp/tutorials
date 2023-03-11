package com.baeldung.javabasics;

import lombok.Data;

@Data
public class Address implements Cloneable {
    private String street;
    private String city;

    public Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    public Address clone() throws CloneNotSupportedException {
        return (Address) super.clone();
    }

}
