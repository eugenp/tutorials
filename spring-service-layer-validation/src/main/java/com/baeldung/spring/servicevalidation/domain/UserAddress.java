package com.baeldung.spring.servicevalidation.domain;

import javax.validation.constraints.NotBlank;
public class UserAddress {
    
    @NotBlank
    private String countryCode;
    
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
   
}
