package com.baeldung.differenttypesofbeaninjection;

import org.springframework.stereotype.Component;

@Component
public class Address {

    public String getAddress() {
        return "USA";
    }

}
