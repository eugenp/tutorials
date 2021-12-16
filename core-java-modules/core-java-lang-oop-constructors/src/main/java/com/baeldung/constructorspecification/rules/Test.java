package com.baeldung.constructorspecification.rules;

/**
 * Created by arash on 16.12.21.
 */

class Test {
    public Test(){
        Test t = new Test();
    }

    public static void main(String[] args) {
        new Test(); // java.lang.StackOverflowError
    }
}