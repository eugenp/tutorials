package com.baeldung.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Calculator {

    private Addition addition;

    @Autowired
    public Calculator(Addition addition) {
        System.out.println("Inside Calculator constructor.");
        this.addition = addition;
    }

    public int calculateAddition(int a, int b) {
        return addition.calculateAddition(a, b);
    }

}
