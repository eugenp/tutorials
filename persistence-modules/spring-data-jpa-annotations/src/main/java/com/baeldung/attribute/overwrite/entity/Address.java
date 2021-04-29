package com.baeldung.attribute.overwrite.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String name;
    private String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
