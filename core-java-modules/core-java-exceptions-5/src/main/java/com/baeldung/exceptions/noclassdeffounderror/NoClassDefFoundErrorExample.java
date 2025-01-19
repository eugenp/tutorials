package com.baeldung.exceptions.noclassdeffounderror;

import com.baeldung.exceptions.noclassdeffounderror.ClassWithInitErrors;

public class NoClassDefFoundErrorExample {
    public ClassWithInitErrors getClassWithInitErrors() {
        ClassWithInitErrors test;
        try {
            test = new ClassWithInitErrors();
        } catch (Throwable t) {
            System.out.println(t);
        }
        test = new ClassWithInitErrors();
        return test;
    }
}