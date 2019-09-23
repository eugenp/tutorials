package com.baeldung.hexagonal;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class Resources {

    @Produces
    public PersonService producePersonService(InjectionPoint injectionPoint) {
        return new PersonServiceImpl();
    }

}
