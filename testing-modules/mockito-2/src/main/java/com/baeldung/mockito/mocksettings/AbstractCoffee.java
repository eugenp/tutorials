package com.baeldung.mockito.mocksettings;

public abstract class AbstractCoffee {

    protected String name;

    protected AbstractCoffee(String name) {
        this.name = name;
    }

    protected String getName() {
        return name;
    }

}
