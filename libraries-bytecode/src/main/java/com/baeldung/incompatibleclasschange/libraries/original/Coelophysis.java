package com.baeldung.incompatibleclasschange.libraries.original;

public class Coelophysis extends Dinosaur {
    public void mySpecies() {
        species("My species is Coelophysis of the Triassic Period");
    }

    public static void main(String[] args) {
        Coelophysis coelophysis = new Coelophysis();
        coelophysis.mySpecies();
    }
}
