package com.baeldung.jnago;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface GoLibrary extends Library {
    void SayHello();
    int AddNumbers(int a, int b);
    Pointer Greet(String name);
}