package com.baeldung.jersey.server.config;

import org.glassfish.jersey.server.ResourceConfig;

import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("/*")
public class ServerConfig extends ResourceConfig {

    public ServerConfig() {
        packages("com.baeldung.jersey.server");
    }

}
