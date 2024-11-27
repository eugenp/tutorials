package com.baeldung.libraries.h2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.*;

public class H2JUnitTest {

    @Test
    public static void whenConnectingToSpecificSchema_ShouldReturnTheSpecificSchema() throws Exception {
        Connection conn = DriverManager.getConnection(
          "jdbc:h2:~/TEST_DB;INIT=CREATE SCHEMA IF NOT EXISTS TEST_SCHEMA\\;SET SCHEMA TEST_SCHEMA",
          "sa",
          "");
      
        Statement stmt=conn.createStatement();
      
        ResultSet rs=stmt.executeQuery("SELECT CURRENT_SCHEMA");
        rs.first();
      
        String actualSchema = rs.getString(1);
        String expectedSchema = new String("TEST_SCHEMA");

        Assertions.assertTrue(actualSchema.equals(expectedSchema));
    }

}
