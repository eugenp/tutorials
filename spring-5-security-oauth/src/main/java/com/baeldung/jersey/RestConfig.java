package com.baeldung.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class RestConfig extends ResourceConfig {
    public RestConfig() {
        register(JerseyResource.class);
    }
}
