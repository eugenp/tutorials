package com.baeldung.hexagonal;

/**
 * Port (interface) that defines a greeting contract.
 */
public interface GreeterPort {

    /**
     * Defines a greet contract.
     *
     * @param title Person's title.
     * @param name  Person's name.
     * @return The customized greeting.
     */
    String greet(String title, String name);

}
