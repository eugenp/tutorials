package com.baeldung.differenttypesofbeaninjection;

import org.springframework.stereotype.Component;

@Component
public class Salary {

    public String process(String amount) {
        return amount + " transferred";
    }

}
