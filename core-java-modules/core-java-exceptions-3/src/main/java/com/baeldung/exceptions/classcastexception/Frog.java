package com.baeldung.exceptions.classcastexception;

public class Frog extends Reptile {

    @Override
    public String getName() {
        return super.getName() + ": Frog";
    }
}
