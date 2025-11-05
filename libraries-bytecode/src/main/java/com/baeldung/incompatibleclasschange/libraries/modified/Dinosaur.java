package com.baeldung.incompatibleclasschange.libraries.modified;

public class Dinosaur {

    /**
     * This is the modified version of the library where the species() method is made static.
     * The original version of the same hierarchy is not static.
     *
     */
    public static void species(String sp) {
        if (sp == null) {
            System.out.println("I am a generic Dinosaur");
        } else {
            System.out.println(sp);
        }
    }
}