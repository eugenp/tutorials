package com.baeldung.maven.integration;

import org.glassfish.jersey.server.ResourceConfig;

public class EndpointConfig extends ResourceConfig {
    public EndpointConfig() {
        register(RestEndpoint.class);
    }
}
