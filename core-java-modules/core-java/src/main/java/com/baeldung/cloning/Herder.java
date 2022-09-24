package com.baeldung.cloning;

public class Herder implements Cloneable {
    
    String name;
    
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Herder(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
