package com.baeldung.clonable;

import java.util.List;

public class Pet implements Cloneable {

    private String name;
    private Human owner;

    public Pet(String name, Human owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Human getOwner() {
        return owner;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
