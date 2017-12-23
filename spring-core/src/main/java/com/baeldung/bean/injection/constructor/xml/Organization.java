package com.baeldung.bean.injection.constructor.xml;

public class Organization {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Organization [name=" + name + "]";
    }

    public void setup() {
        this.name = "Baeldung";
    }

}
