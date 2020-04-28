package com.baeldung.nestedclass;

import org.junit.Test;

abstract class SimpleAbstractClass {
    abstract void run();
}

public class AnonymousInnerTest {

    @Test
    public void run() {
        SimpleAbstractClass simpleAbstractClass = new SimpleAbstractClass() {
            void run() {
                System.out.println("Running Anonymous Class...");
            }
        };
        simpleAbstractClass.run();
    }
}