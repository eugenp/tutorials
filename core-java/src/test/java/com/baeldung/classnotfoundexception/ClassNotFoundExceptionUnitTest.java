package com.baeldung.classnotfoundexception;

import org.junit.Test;

public class ClassNotFoundExceptionUnitTest {

    @Test(expected = ClassNotFoundException.class)
    public void givenNoDriversInClassPath_whenLoadDrivers_thenClassNotFoundException() throws ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
    }
}