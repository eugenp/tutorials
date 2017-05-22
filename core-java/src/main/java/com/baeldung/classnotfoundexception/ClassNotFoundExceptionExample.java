package com.baeldung.classnotfoundexception;

public class ClassNotFoundExceptionExample {
    public void loadDrivers() throws ClassNotFoundException{
        Class.forName("oracle.jdbc.driver.OracleDriver");
    }
}
