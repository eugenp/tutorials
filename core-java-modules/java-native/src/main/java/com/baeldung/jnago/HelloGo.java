package com.baeldung.jnago;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class HelloGo {
    public static void main(String[] args) {
        GoLibrary goLibrary = Native.load("hello", GoLibrary.class);
        goLibrary.SayHello();
        // Adding two int numbers
        int result = goLibrary.AddNumbers(2,3);
        System.out.printf("Result is %d%n", result);
        // Passing and returning string
        Pointer ptr = goLibrary.Greet("Alice");
        String greeting = ptr.getString(0);
        System.out.println(greeting);
        Native.free(Pointer.nativeValue(ptr));
    }
}