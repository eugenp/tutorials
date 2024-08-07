package com.baeldung.quarkus;

import jakarta.annotation.security.RolesAllowed;

public class MyServiceImpl implements MyService {

    @Override
    @RolesAllowed("admin")
    public String hello() {
        return "Hello from Quarkus REST";
    }
}
