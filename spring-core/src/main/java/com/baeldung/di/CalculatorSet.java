package com.baeldung.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalculatorSet {

    private Addition addition;

    public Addition getAddition() {
        return addition;
    }

    @Autowired
    public void setAddition(Addition addition) {
        System.out.println("Inside addition Setter.");
        this.addition = addition;
    }

    public int calculateAddition(int a, int b) {
        return addition.calculateAddition(a, b);
    }
    
}
