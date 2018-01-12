package com.baeldung.differenttypesofbeaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeerService {

    private Bar bar;

    @Autowired
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public String order() {
        return bar.order("Beer");
    }
}
