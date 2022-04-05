package com.baeldung.exceptions.illegalaccesserror;

public class IllegalAccessErrorSolved {

    interface BaeldungSolved {
        public default void foobar() {
            System.out.println("This is a default method.");
        }
    }

    class SuperSolved {
        public void foobar() {
            System.out.println("SuperClass method foobar");
        }
    }

    class MySubClassSolved extends SuperSolved implements BaeldungSolved {

    }
}