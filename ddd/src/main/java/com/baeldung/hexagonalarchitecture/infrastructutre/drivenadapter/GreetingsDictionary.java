package com.baeldung.hexagonalarchitecture.infrastructutre.drivenadapter;

import com.baeldung.hexagonalarchitecture.application.boundary.drivenports.IObtainGreetings;

public class GreetingsDictionary implements IObtainGreetings {
        @Override public String[] getGreetingsForLanguage(String language) {
                if ("fr".equals(language)) {
                        return new String[] { "Bonjour!", "Salut!", "Coucou!", "Allo" };
                }
                if ("sp".equals(language)) {
                        return new String[] { "Hola", "Buenos días!", "Buenas tardes", "Adiós" };
                } else {
                        return new String[] { "Hey there", "Hello", "Good Morning", "What's Up" };
                }
        }
}
