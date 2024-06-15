package com.baeldung.incompatibleclasschange.libraries.original;

public class Dinosaur {
    public void species(String sp) {
        if (sp == null) {
            System.out.println("I am a generic Dinosaur");
        } else {
            System.out.println(sp);
        }
    }
}
