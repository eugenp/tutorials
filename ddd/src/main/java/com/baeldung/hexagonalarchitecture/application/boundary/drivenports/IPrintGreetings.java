package com.baeldung.hexagonalarchitecture.application.boundary.drivenports;

/**
 * Driven port, right side port for printing greetings to an output
 * outside the hexagon, e.g. the console.
 *
 */
public interface IPrintGreetings {
        void printGreetings(String[] greetings);
}
