package com.baeldung.classnotfoundexception;

import org.junit.Test;

public class ClassNotFoundExceptionTest {

    @Test(expected = ClassNotFoundException.class)
    public void givenNoDriversInClassPath_whenLoadDrivers_thenClassNotFoundException() throws ClassNotFoundException {
        ClassNotFoundExceptionExample test = new ClassNotFoundExceptionExample();
        test.loadDrivers();
    }
}
