package com.baeldung.hexagonal;

/**
 * Adapter (concrete implementation) of @{@link GreeterPort} interface.
 */
public class GreeterAdapter implements GreeterPort {

    @Override
    public String greet(String title, String name) {
        return String.format("Hello %s %s!", title, name);
    }

}
