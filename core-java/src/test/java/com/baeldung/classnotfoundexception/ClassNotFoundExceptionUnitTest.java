package com.baeldung.classnotfoundexception;

import org.junit.Test;

/**
 * 类找不到异常
 */
public class ClassNotFoundExceptionUnitTest {

    @Test(expected = ClassNotFoundException.class)
    public void givenNoDriversInClassPath_whenLoadDrivers_thenClassNotFoundException() throws ClassNotFoundException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }
}