package com.baeldung.injectiontypes;

public class MyService {

    private final Dependency1 d1;
    private final Dependency2 d2;

    public MyService(Dependency1 d1, Dependency2 d2) {
        this.d1 = d1;
        this.d2 = d2;
    }

    public Dependency1 getD1() {
        return d1;
    }

    public Dependency2 getD2() {
        return d2;
    }
}
