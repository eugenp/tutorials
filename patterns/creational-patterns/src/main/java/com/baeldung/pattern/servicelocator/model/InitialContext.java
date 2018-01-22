package com.baeldung.pattern.servicelocator.model;

public class InitialContext {
    public Object lookup(String jndiName) {

        if (jndiName.equalsIgnoreCase("SERVICE1")) {
            return new Service1();
        } else if (jndiName.equalsIgnoreCase("SERVICE2")) {
            return new Service2();
        }
        return null;
    }
}
