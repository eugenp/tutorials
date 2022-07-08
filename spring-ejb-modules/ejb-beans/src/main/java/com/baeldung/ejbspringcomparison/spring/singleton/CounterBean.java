package com.baeldung.ejbspringcomparison.spring.singleton;

import org.springframework.stereotype.Component;

@Component
public class CounterBean {
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
