package com.baeldung.jersey.exceptionhandling;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import com.baeldung.jersey.exceptionhandling.rest.exceptions.IllegalArgumentExceptionMapper;
import com.baeldung.jersey.exceptionhandling.rest.exceptions.ServerExceptionMapper;

@ApplicationPath("/exception-handling/*")
public class ExceptionHandlingConfig extends ResourceConfig {

    public ExceptionHandlingConfig() {
        packages("com.baeldung.jersey.exceptionhandling.rest");
        register(IllegalArgumentExceptionMapper.class);
        register(ServerExceptionMapper.class);
    }

}
