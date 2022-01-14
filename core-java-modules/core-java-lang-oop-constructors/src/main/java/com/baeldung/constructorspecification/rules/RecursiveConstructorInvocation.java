package com.baeldung.constructorspecification.rules;

/**
 * Created by arash on 16.12.21.
 */

class RecursiveConstructorInvocation {

    public RecursiveConstructorInvocation() {
        RecursiveConstructorInvocation rci = new RecursiveConstructorInvocation();
    }

    public static void main(String[] args) {
        new RecursiveConstructorInvocation(); // java.lang.StackOverflowError
    }
}