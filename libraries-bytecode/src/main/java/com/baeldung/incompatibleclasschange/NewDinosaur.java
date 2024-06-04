package com.baeldung.incompatibleclasschange;

import org.dinosaur.Dinosaur;

public class NewDinosaur extends Dinosaur {
    public void mySpecies() {
        species("My species is New dinosaur");
    }

    public static void main(String[] args) {
        NewDinosaur md = new NewDinosaur();
        md.mySpecies();
    }
}
