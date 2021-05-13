package com.baeldung.staticClassSingleton;

public class StaticClassExample {
    private final String outerGreeting;

    public StaticClassExample(String outerGreeting) {
        this.outerGreeting = outerGreeting;
    }

    static class innerStaticClass {
        private final String greeting;

        innerStaticClass(String greeting) {
            this.greeting = greeting;
        }

        public String getGreeting() {
            return greeting;
        }
    }
}
