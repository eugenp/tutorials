package com.baeldung.resultset2json;

import static com.baeldung.resultset2json.ResultSet2JSON.resultSet2JdbcMethod1;
import static com.baeldung.resultset2json.ResultSet2JSON.resultSet2JdbcMethod2;
import static com.baeldung.resultset2json.ResultSet2JSON.resultSet2JdbcMethod3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResultSet2JSONUnitTest {
  JSONObject object = new JSONObject(
    "{\"records\":[[\"doe1\",\"7173\",\"John\",\"Doe\"],[\"smith3\",\"3722\",\"Dana\",\"Smith\"],[\"john22\",\"5490\",\"John\",\"Wang\"]],\"fields\":[{\"schema\":\"PUBLIC\",\"name\":\"USERNAME\",\"type\":\"VARCHAR\",\"table\":\"WORDS\"},{\"schema\":\"PUBLIC\",\"name\":\"ID\",\"type\":\"VARCHAR\",\"table\":\"WORDS\"},{\"schema\":\"PUBLIC\",\"name\":\"First name\",\"type\":\"VARCHAR\",\"table\":\"WORDS\"},{\"schema\":\"PUBLIC\",\"name\":\"Last name\",\"type\":\"VARCHAR\",\"table\":\"WORDS\"}]}");

  JSONArray array = new JSONArray(
    "[{\"USERNAME\":\"doe1\",\"First name\":\"John\",\"ID\":\"7173\",\"Last name\":\"Doe\"},{\"USERNAME\":\"smith3\",\"First name\":\"Dana\",\"ID\":\"3722\",\"Last name\":\"Smith\"},{\"USERNAME\":\"john22\",\"First name\":\"John\",\"ID\":\"5490\",\"Last name\":\"Wang\"}]");

  @Test
  void method1() throws SQLException, ClassNotFoundException {
    Class.forName("org.h2.Driver");
    Connection dbConnection = DriverManager.getConnection("jdbc:h2:mem:rs2jdbc1", "user", "password");

    // Create a table
    Statement stmt = dbConnection.createStatement();
    stmt.execute("CREATE TABLE words AS SELECT * FROM CSVREAD('./example.csv')");
    ResultSet resultSet = stmt.executeQuery("SELECT * FROM words");

    JSONArray result1 = resultSet2JdbcMethod1(resultSet);

    resultSet.close();

    assertTrue(array.similar(result1));
  }

  @Test
  void method2() throws SQLException, ClassNotFoundException {
    Class.forName("org.h2.Driver");
    Connection dbConnection = DriverManager.getConnection("jdbc:h2:mem:rs2jdbc2", "user", "password");
    // Create a table
    Statement stmt = dbConnection.createStatement();
    stmt.execute("CREATE TABLE words AS SELECT * FROM CSVREAD('./example.csv')");
    ResultSet resultSet = stmt.executeQuery("SELECT * FROM words");

    JSONObject result2 = resultSet2JdbcMethod2(resultSet, dbConnection);

    resultSet.close();

    assertTrue(object.similar(result2));
  }

  @Test
  void method3() throws SQLException, ClassNotFoundException {
    Class.forName("org.h2.Driver");
    Connection dbConnection = DriverManager.getConnection("jdbc:h2:mem:rs2jdbc3", "user", "password");
    // Create a table
    Statement stmt = dbConnection.createStatement();
    stmt.execute("CREATE TABLE words AS SELECT * FROM CSVREAD('./example.csv')");
    ResultSet resultSet = stmt.executeQuery("SELECT * FROM words");

    JSONArray result3 = resultSet2JdbcMethod3(resultSet, dbConnection);

    resultSet.close();

    assertTrue(array.similar(result3));
  }

}
