package com.baeldung.annotations.globalmethod;

import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;

@Component
public class DifferentClass {
    @RolesAllowed("USER")
    public String differentJsr250Hello() {
        return "Hello Jsr250";
    }
}
