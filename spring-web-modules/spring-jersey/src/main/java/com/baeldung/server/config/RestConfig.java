package com.baeldung.server.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import com.baeldung.server.exception.AlreadyExistsExceptionHandler;
import com.baeldung.server.exception.NotFoundExceptionHandler;
import com.baeldung.server.rest.EmployeeResource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/resources")
public class RestConfig extends Application {
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(Arrays.asList(EmployeeResource.class, NotFoundExceptionHandler.class, AlreadyExistsExceptionHandler.class));
    }
}