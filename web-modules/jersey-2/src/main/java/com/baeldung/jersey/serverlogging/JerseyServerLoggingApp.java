package com.baeldung.jersey.serverlogging;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class JerseyServerLoggingApp extends ResourceConfig {

    public JerseyServerLoggingApp() {
        register(LoggingResource.class);

        register(new LoggingFeature(
            Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME), 
            Level.INFO, 
            LoggingFeature.Verbosity.PAYLOAD_ANY, 
            8192));

        register(CustomServerLoggingFilter.class);
    }
}
