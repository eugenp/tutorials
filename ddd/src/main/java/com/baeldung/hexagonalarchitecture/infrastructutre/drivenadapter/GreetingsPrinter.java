package com.baeldung.hexagonalarchitecture.infrastructutre.drivenadapter;

import com.baeldung.hexagonalarchitecture.application.boundary.drivenports.IPrintGreetings;

import java.util.Objects;

public class GreetingsPrinter implements IPrintGreetings {
        @Override public void printGreetings(String[] greetings) {
                Objects.requireNonNull(greetings);
                for (String greeting : greetings) {
                        System.out.println(greeting);
                }
                System.out.println("");
        }
}
