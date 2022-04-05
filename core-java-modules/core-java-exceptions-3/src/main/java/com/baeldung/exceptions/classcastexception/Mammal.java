package com.baeldung.exceptions.classcastexception;

public class Mammal implements Animal {

    @Override
    public String getName() {
        return "Mammal";
    }
}
