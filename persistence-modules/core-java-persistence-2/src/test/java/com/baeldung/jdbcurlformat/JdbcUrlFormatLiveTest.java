package com.baeldung.jdbcurlformat;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

public class JdbcUrlFormatLiveTest {
    @Test
    public void givenOracleSID_thenCreateConnectionObject() {
        String oracleJdbcUrl = "jdbc:oracle:thin:@myoracle.db.server:1521:my_sid";
        String username = "dbUser";
        String password = "1234567";
        try (Connection conn = DriverManager.getConnection(oracleJdbcUrl, username, password)) {
            assertNotNull(conn);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    @Test
    public void givenOracleServiceName_thenCreateConnectionObject() {
        String oracleJdbcUrl = "jdbc:oracle:thin:@//myoracle.db.server:1521/my_servicename";
        String username = "dbUser";
        String password = "1234567";
        try (Connection conn = DriverManager.getConnection(oracleJdbcUrl, username, password)) {
            assertNotNull(conn);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    @Test
    public void givenOracleTnsnames_thenCreateConnectionObject() {
        String oracleJdbcUrl = "jdbc:oracle:thin:@" +
            "(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)" +
            "(HOST=myoracle.db.server)(PORT=1521))" +
            "(CONNECT_DATA=(SERVICE_NAME=my_servicename)))";
        String username = "dbUser";
        String password = "1234567";
        try (Connection conn = DriverManager.getConnection(oracleJdbcUrl, username, password)) {
            assertNotNull(conn);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    @Test
    public void givenMysqlDb_thenCreateConnectionObject() {
        String jdbcUrl = "jdbc:mysql://mysql.db.server:3306/my_database?useSSL=false&serverTimezone=UTC";
        String username = "dbUser";
        String password = "1234567";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            assertNotNull(conn);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    @Test
    public void givenMssqlDb_thenCreateConnectionObject() {
        String jdbcUrl = "jdbc:sqlserver://mssql.db.server\\mssql_instance;databaseName=my_database";
        String username = "dbUser";
        String password = "1234567";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            assertNotNull(conn);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    @Test
    public void givenPostgreSqlDb_thenCreateConnectionObject() {
        String jdbcUrl = "jdbc:postgresql://postgresql.db.server:5430/my_database?ssl=true&loglevel=2";
        String username = "dbUser";
        String password = "1234567";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            assertNotNull(conn);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
}
