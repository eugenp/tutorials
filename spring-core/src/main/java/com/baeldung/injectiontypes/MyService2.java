package com.baeldung.injectiontypes;

import java.beans.ConstructorProperties;

public class MyService2 {

    private final Dependency d1;
    private final Dependency d2;

    @ConstructorProperties({"d1", "d2"})
    public MyService2(Dependency d1, Dependency d2) {
        this.d1 = d1;
        this.d2 = d2;
    }

    public Dependency getD1() {
        return d1;
    }

    public Dependency getD2() {
        return d2;
    }
}
