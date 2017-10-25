package com.baeldung.beaninjection;

import org.springframework.stereotype.Component;

@Component
public class Address {

    private String street;

    /**
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street the street to set
     */
    public void setStreet(String street) {
        this.street = street;
    }

}
