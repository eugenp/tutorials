package com.baeldung.beaninjection;

public class DependencyBean {

    private String name;

    public DependencyBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}