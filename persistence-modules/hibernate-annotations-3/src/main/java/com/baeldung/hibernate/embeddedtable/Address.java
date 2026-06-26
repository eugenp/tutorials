package com.baeldung.hibernate.embeddedtable;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
	
    private String street;
    private String city;
    private String code;

    public void setStreet(String street) {
        this.street = street;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
}
