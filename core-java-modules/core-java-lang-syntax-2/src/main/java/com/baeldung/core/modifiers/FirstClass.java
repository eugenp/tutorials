package com.baeldung.core.modifiers;

public class FirstClass {

    protected String name;
    
    protected FirstClass(String name) {
        this.name = name;
    }

    protected String getName() {
        return name;
    }

    protected static class InnerClass {
        public InnerClass() {}
    }
}
