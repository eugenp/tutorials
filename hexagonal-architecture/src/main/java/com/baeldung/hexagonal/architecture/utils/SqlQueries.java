package com.baeldung.hexagonal.architecture.utils;

public class SqlQueries {

    public static String insertPersonQuery = "INSERT INTO PERSON VALUES(?,?)";
    public static String getPersonQuery = "SELECT ID, NAME FROM PERSON WHERE ID=?";
}
