package com.baeldung.typechecker;

import org.checkerframework.checker.fenum.qual.Fenum;

public class FakeNumExample {

    @Fenum("country") static final String ITALY = "IT";
    @Fenum("country") static final String US = "US";
    @Fenum("country") static final String UNITED_KINGDOM = "UK";
    @Fenum("country") static final String ARGENTINA = "AG";

    void greet(@Fenum("country") String country){
        System.out.println("Hello " + country);
    }

}
