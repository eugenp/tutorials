package com.baeldung.hexagonal.web;

import com.baeldung.hexagonal.domain.DomainBinder;
import com.baeldung.hexagonal.jpa.JPABinder;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Configures the JAX-RS person application. This is where developers have an opportunity to swap in
 * different technology adapters using HK2 binders.
 */
class PersonResourceConfiguration extends ResourceConfig {

    PersonResourceConfiguration() {
        register(new JacksonBinder());
        register(new JPABinder());
        register(new DomainBinder());
        register(PersonResource.class);
        register(ObjectMapperProvider.class);
    }

}
