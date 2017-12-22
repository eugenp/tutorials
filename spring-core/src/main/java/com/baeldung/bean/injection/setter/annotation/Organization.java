package com.baeldung.bean.injection.setter.annotation;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component(value = "organization")
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

    @PostConstruct
    public void setup() {
        this.name = "Baeldung";
    }

}
