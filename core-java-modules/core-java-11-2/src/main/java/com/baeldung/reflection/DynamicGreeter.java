package com.baeldung.reflection;

import java.lang.annotation.Annotation;

public class DynamicGreeter implements Greeter {
    
    private String greet;

    public DynamicGreeter(String greet) {
        this.greet = greet;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return DynamicGreeter.class;
    }

    @Override
    public String greet() {
        return greet;
    }

}
