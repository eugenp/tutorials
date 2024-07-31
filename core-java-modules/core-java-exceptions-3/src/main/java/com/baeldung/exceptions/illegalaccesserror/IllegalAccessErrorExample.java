package com.baeldung.exceptions.illegalaccesserror;

public class IllegalAccessErrorExample {

    interface Baeldung {
        public default void foobar() {
            System.out.println("This is a default method.");
        }
    }

    class Super {
        private void foobar() {
            System.out.println("SuperClass method foobar");
        }
    }

    class MySubClass extends Super implements Baeldung {

    }
}