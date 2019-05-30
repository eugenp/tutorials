package com.baeldung.hexagonal.domain;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class DomainBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(PersonServiceImpl.class).to(PersonService.class);
    }
}
