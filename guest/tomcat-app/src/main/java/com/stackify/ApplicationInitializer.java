package com.stackify;

import org.glassfish.jersey.server.ResourceConfig;

public class ApplicationInitializer extends ResourceConfig {
    public ApplicationInitializer() {
        packages("com.stackify.services");
    }
}
