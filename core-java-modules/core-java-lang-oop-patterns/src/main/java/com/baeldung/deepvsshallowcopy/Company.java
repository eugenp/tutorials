package com.baeldung.deepvsshallowcopy;

public class Company implements Cloneable {

    private String name;

    public Company(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Company clone() {
        try {
            return (Company) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Company(name);
        }
    }
}
