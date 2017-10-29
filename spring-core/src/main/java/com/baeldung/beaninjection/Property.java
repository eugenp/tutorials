package com.baeldung.beaninjection;

@Component
public class Property {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
