package com.baeldung.spring.spel.examples;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("spelConditional")
public class SpelConditional {
    @Value("#{false ? 'There was true value' : 'Something went wrong. There was false value'}")
    private String ternary;

    @Value("#{someCar.model != null ? someCar.model : 'Unknown model'}")
    private String ternary2;

    @Value("#{someCar.model?:'Unknown model'}")
    private String elvis;

    public String getTernary() {
        return ternary;
    }

    public void setTernary(String ternary) {
        this.ternary = ternary;
    }

    public String getTernary2() {
        return ternary2;
    }

    public void setTernary2(String ternary2) {
        this.ternary2 = ternary2;
    }

    public String getElvis() {
        return elvis;
    }

    public void setElvis(String elvis) {
        this.elvis = elvis;
    }

    @Override
    public String toString() {
        return "SpelConditional{" +
                "ternary='" + ternary + '\'' +
                ", ternary2='" + ternary2 + '\'' +
                ", elvis='" + elvis + '\'' +
                '}';
    }
}
