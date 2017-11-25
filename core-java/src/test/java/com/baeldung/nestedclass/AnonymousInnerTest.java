package com.baeldung.nestedclass;

import org.junit.Test;

public class AnonymousInnerTest {

    @Test
    public void whenRunAnonymousClass_thenCorrect() {
        SimpleAbstractClass simpleAbstractClass = new SimpleAbstractClass() {
            void run() {
                System.out.println("Running Anonymous Class...");
            }
        };
        simpleAbstractClass.run();
    }
}