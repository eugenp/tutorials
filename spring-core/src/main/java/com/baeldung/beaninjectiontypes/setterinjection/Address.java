package com.baeldung.beaninjectiontypes.setterinjection;

import org.springframework.stereotype.Component;

@Component
public class Address {

    private String city;

    private String country;

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return String.format("%s, %s", city, country);
    }

}
