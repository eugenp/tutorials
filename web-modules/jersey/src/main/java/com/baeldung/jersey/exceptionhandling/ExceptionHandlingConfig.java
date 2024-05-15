package com.baeldung.jersey.exceptionhandling;

import org.glassfish.jersey.server.ResourceConfig;

import com.baeldung.jersey.exceptionhandling.rest.exceptions.IllegalArgumentExceptionMapper;
import com.baeldung.jersey.exceptionhandling.rest.exceptions.ServerExceptionMapper;

import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("/exception-handling/*")
public class ExceptionHandlingConfig extends ResourceConfig {

    public ExceptionHandlingConfig() {
        packages("com.baeldung.jersey.exceptionhandling.rest");
        register(IllegalArgumentExceptionMapper.class);
        register(ServerExceptionMapper.class);
    }

}
