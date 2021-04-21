package com.baeldung.signature;

import java.io.Serializable;

public class OverloadingErrors<T extends Serializable> {

    public void print() {
        System.out.println("Signature is: print()");
    }

    /*
    // Uncommenting this method will lead to a compilation error: java: method print() is already defined in class
    // The method signature is independent from return type
    public int print() {
        System.out.println("Signature is: print()");
        return 0;
    }
    */

    /*
    // Uncommenting this method will lead to a compilation error: java: method print() is already defined in class
    // The method signature is independent from modifiers
    private final void print() {
        System.out.println("Signature is: print()");
    }
    */

    /*
    // Uncommenting this method will lead to a compilation error: java: method print() is already defined in class
    // The method signature is independent from thrown exception declaration
    public void print() throws IllegalStateException {
        System.out.println("Signature is: print()");
        throw new IllegalStateException();
    }
    */

    public void print(int parameter) {
        System.out.println("Signature is: print(int)");
    }

    /*
    // Uncommenting this method will lead to a compilation error: java: method print(int) is already defined in class
    // The method signature is independent from parameter names
    public void print(int anotherParameter) {
        System.out.println("Signature is: print(int)");
    }
    */

    public void printElement(T t) {
        System.out.println("Signature is: printElement(T)");
    }

    /*
    // Uncommenting this method will lead to a compilation error: java: name clash: printElement(java.io.Serializable) and printElement(T) have the same erasure
    // Even though the signatures appear different, the compiler cannot statically bind the correct method after type erasure
    public void printElement(Serializable o) {
        System.out.println("Signature is: printElement(Serializable)");
    }
    */

    public void print(Object... parameter) {
        System.out.println("Signature is: print(Object...)");
    }

    /*
    // Uncommenting this method will lead to a compilation error: java cannot declare both sum(Object...) and sum(Object[])
    // Even though the signatures appear different, after compilation they both resolve to sum(Object[])
    public void print(Object[] parameter) {
        System.out.println("Signature is: print(Object[])");
    }
    */
}
