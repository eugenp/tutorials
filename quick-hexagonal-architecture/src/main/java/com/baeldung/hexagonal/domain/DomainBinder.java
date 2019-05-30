package com.baeldung.hexagonal.domain;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * HK2 binder which provides an instance of {@link PersonService} for adapters to use.
 */
public class DomainBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(PersonServiceImpl.class).to(PersonService.class);
    }
}
