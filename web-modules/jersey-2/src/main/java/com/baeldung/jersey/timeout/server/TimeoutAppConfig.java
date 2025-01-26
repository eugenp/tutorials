package com.baeldung.jersey.timeout.server;

import org.glassfish.jersey.server.ResourceConfig;

public class TimeoutAppConfig extends ResourceConfig {

    public TimeoutAppConfig() {
        packages(JerseyTimeoutServerApp.class.getPackage()
            .getName());
    }
}
