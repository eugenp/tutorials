package com.baeldung.ejb.spring.comparison.ejb.singleton;

import javax.ejb.Singleton;

@Singleton
public class CounterEJB implements CounterEJBRemote {

    private int count = 1;
    private String name;

    public int count() {
        return count++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}