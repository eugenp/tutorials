package com.baeldung.resultsettomap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ResultSetToMapUnitTest {
    private static Connection connection = null;
    private static final String JDBC_URL = "jdbc:h2:mem:testDatabase";
    private static final String USERNAME = "dbUser";
    private static final String PASSWORD = "dbPassword";

    @Before
    public void setup() throws Exception {
        connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        initialDataSetup();
    }

    private void initialDataSetup() throws SQLException {
        Statement statement = connection.createStatement();
        String ddlQuery = "CREATE TABLE employee (empId INTEGER not null, empName VARCHAR(50), empCity VARCHAR(50), PRIMARY KEY (empId))";
        statement.execute(ddlQuery);
        List<String> sqlQueryList = Arrays.asList("INSERT INTO employee VALUES (1, 'Steve','London')", "INSERT INTO employee VALUES (2, 'John','London')", "INSERT INTO employee VALUES (3, 'David', 'Sydney')",
            "INSERT INTO employee VALUES (4, 'Kevin','London')", "INSERT INTO employee VALUES (5, 'Jade', 'Sydney')");

        for (String query : sqlQueryList) {
            statement.execute(query);
        }
    }

    @Test
    public void whenUsingContainsKey_thenConvertResultSetToMap() throws SQLException {
        ResultSet resultSet = connection.prepareStatement("SELECT * FROM employee")
            .executeQuery();
        Map<String, List<String>> valueMap = new HashMap<>();

        while (resultSet.next()) {
            String empCity = resultSet.getString("empCity");
            String empName = resultSet.getString("empName");
            if (!valueMap.containsKey(empCity)) {
                valueMap.put(empCity, new ArrayList<>());
            }
            valueMap.get(empCity)
                .add(empName);
        }
        assertEquals(3, valueMap.get("London")
            .size());
    }

    @Test
    public void whenUsingComputeIfAbsent_thenConvertResultSetToMap() throws SQLException {
        ResultSet resultSet = connection.prepareStatement("SELECT * FROM employee")
            .executeQuery();
        Map<String, List<String>> valueMap = new HashMap<>();

        while (resultSet.next()) {
            String empCity = resultSet.getString("empCity");
            String empName = resultSet.getString("empName");
            valueMap.computeIfAbsent(empCity, data -> new ArrayList<>())
                .add(empName);
        }
        assertEquals(3, valueMap.get("London")
            .size());
    }

    @Test
    public void whenUsingDbUtils_thenConvertResultSetToMap() throws SQLException {

        ResultSetHandler<Map<String, List<String>>> handler = new ResultSetHandler<Map<String, List<String>>>() {
            public Map<String, List<String>> handle(ResultSet resultSet) throws SQLException {
                Map<String, List<String>> result = new HashMap<>();
                while (resultSet.next()) {
                    String empCity = resultSet.getString("empCity");
                    String empName = resultSet.getString("empName");
                    result.computeIfAbsent(empCity, data -> new ArrayList<>())
                        .add(empName);
                }
                return result;
            }
        };

        QueryRunner run = new QueryRunner();
        Map<String, List<String>> valueMap = run.query(connection, "SELECT * FROM employee", handler);
        assertEquals(3, valueMap.get("London")
            .size());
    }

    @After
    public void preDestroy() throws Exception {
        connection.close();
    }
}
