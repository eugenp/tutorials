package com.baeldung.ejb.spring.comparison.spring.singleton;

import org.springframework.stereotype.Component;

@Component
public class CounterBean {
    private int count = 1;

    public int count() {
        return count++;
    }
}
