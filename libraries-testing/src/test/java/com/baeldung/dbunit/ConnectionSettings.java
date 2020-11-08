package com.baeldung.dbunit;

public class ConnectionSettings {
    public static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    public static final String JDBC_URL = "jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:dbunit/schema.sql'";
    public static final String USER = "sa";
    public static final String PASSWORD = "";
}
