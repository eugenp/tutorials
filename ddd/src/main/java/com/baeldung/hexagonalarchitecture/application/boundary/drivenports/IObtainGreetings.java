package com.baeldung.hexagonalarchitecture.application.boundary.drivenports;

/**
 * Driven port, right side port for obtaining greetings, outside
 * the hexagon.
 */
public interface IObtainGreetings {
       String[] getGreetingsForLanguage(String language);
}
