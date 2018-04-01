package com.baeldung.beaninjectiontypes.constructorinjection;

import org.springframework.stereotype.Component;

@Component
public class Address {

    private String city;

    private String country;

    public Address(String city, String country) {
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return String.format("%s, %s", city, country);
    }

}
