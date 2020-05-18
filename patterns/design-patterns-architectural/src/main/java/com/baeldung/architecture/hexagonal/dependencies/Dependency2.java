package com.baeldung.architecture.hexagonal.dependencies;

public class Dependency2 {
    public void accept(String s) {
        System.out.println(String.format("Called %s with %s", this.getClass()
            .getSimpleName(), s));
    }
}
