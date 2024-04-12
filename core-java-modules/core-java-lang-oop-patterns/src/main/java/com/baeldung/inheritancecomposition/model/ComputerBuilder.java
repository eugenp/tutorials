package com.baeldung.inheritancecomposition.model;

public abstract class ComputerBuilder {

    public final void buildComputer() {
        addProcessor();
        addMemory();
    }

    public abstract void addProcessor();

    public abstract void addMemory();
}
