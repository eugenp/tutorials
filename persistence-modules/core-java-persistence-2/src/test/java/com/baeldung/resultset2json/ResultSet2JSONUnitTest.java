package com.baeldung.resultset2json;

import static com.baeldung.resultset2json.ResultSet2JSON.resultSet2JdbcMethod1;
import static com.baeldung.resultset2json.ResultSet2JSON.resultSet2JdbcMethod2;
import static com.baeldung.resultset2json.ResultSet2JSON.resultSet2JdbcMethod3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jooq.tools.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class ResultSet2JSONUnitTest {

  @Test
  void method1() throws SQLException, ClassNotFoundException {
    Class.forName("org.h2.Driver");
    Connection dbConnection = DriverManager.getConnection("jdbc:h2:mem:rs2jdbc", "user", "password");

    // Create a table
    Statement stmt = dbConnection.createStatement();
    stmt.execute("CREATE TABLE words AS SELECT * FROM CSVREAD('./example.csv')");
    ResultSet resultSet = stmt.executeQuery("SELECT * FROM words");

    JSONArray result1 = resultSet2JdbcMethod1(resultSet);
    System.out.println(result1);

    resultSet.close();
  }

  @Test
  void method2() throws SQLException, ClassNotFoundException {
    Class.forName("org.h2.Driver");
    Connection dbConnection = DriverManager.getConnection("jdbc:h2:mem:rs2jdbc", "user", "password");
    // Create a table
    Statement stmt = dbConnection.createStatement();
    stmt.execute("CREATE TABLE words AS SELECT * FROM CSVREAD('./example.csv')");
    ResultSet resultSet = stmt.executeQuery("SELECT * FROM words");

    JSONObject result1 = resultSet2JdbcMethod2(resultSet, dbConnection);
    System.out.println(result1);

    resultSet.close();
  }

  @Test
  void method3() throws SQLException, ClassNotFoundException {
    Class.forName("org.h2.Driver");
    Connection dbConnection = DriverManager.getConnection("jdbc:h2:mem:rs2jdbc", "user", "password");
    // Create a table
    Statement stmt = dbConnection.createStatement();
    stmt.execute("CREATE TABLE words AS SELECT * FROM CSVREAD('./example.csv')");
    ResultSet resultSet = stmt.executeQuery("SELECT * FROM words");

    JSONArray result1 = resultSet2JdbcMethod3(resultSet, dbConnection);
    System.out.println(result1);

    resultSet.close();
  }

}
