package com.baeldung.hexagonal.test;

import com.baeldung.hexagonal.PersonService;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class ResourcesTest {

    @Produces
    public PersonService producePersonService(InjectionPoint injectionPoint) {
        return new PersonServiceImplTest();
    }

}
