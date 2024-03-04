package com.baeldung.springvalidation.domain;

import jakarta.validation.constraints.NotBlank;
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
