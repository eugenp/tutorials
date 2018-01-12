package com.baeldung.differenttypesofbeaninjection;

import org.springframework.stereotype.Component;

@Component
public class Bar {

    public String order(String drink) {
        return "Order for " + drink + " received";
    }

}
