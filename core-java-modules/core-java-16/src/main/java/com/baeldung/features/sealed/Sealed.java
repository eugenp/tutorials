package com.baeldung.features.sealed;

public class Sealed {
    public static void main(String... args) {
        JungleAnimal j = new Monkey();

        if (j instanceof Monkey m) {
            // do logic
        } else if (j instanceof Snake s) {
            // do logic
        }
    }
}
