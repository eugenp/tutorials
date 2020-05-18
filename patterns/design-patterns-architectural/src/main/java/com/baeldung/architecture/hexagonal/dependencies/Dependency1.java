package com.baeldung.architecture.hexagonal.dependencies;

public class Dependency1 {
    public void accept(int i) {
        System.out.println(String.format("Called %s with %d", this.getClass()
            .getSimpleName(), i));
    }
}
