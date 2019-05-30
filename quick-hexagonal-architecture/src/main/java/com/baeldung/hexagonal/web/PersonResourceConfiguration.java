package com.baeldung.hexagonal.web;

import com.baeldung.hexagonal.domain.DomainBinder;
import com.baeldung.hexagonal.fake.FakeBinder;
import org.glassfish.jersey.server.ResourceConfig;

class PersonResourceConfiguration extends ResourceConfig {

    PersonResourceConfiguration() {
        register(new JacksonBinder());
        register(new FakeBinder());
        register(new DomainBinder());
        register(PersonResource.class);
        register(ObjectMapperProvider.class);
    }

}
