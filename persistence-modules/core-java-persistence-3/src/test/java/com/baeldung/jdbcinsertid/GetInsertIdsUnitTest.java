package com.baeldung.jdbcinsertid;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GetInsertIdsUnitTest {
    private static Connection connection = null;
    private static final String JDBC_URL = "jdbc:h2:mem:testDB";
    private static final String USERNAME = "dbUser";
    private static final String PASSWORD = "dbPassword";

    @BeforeAll
    static void setup() throws Exception {
        connection = connect(JDBC_URL, USERNAME, PASSWORD);
        populateDB();
    }

    @AfterAll
    static void tearDown() throws SQLException {
        destroyDB();
    }

    @Test
    void givenDBPopulated_WhenGetInsertIds_thenReturnsIds() throws SQLException {
        GetInsertIds getInsertIds = new GetInsertIds();
        List<Long> actualIds = getInsertIds.insertAndReturnIds(connection);
        ResultSet resultSet = connection.prepareStatement("select id from employees")
            .executeQuery();
        List<Long> expectedIds = new ArrayList<>();
        while (resultSet.next()) {
            expectedIds.add(resultSet.getLong(1));
        }

        assertEquals(expectedIds, actualIds);
    }

    private static Connection connect(String url, String user, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        if (connection != null) {
            System.out.println("Connected to database");
        }

        return connection;
    }

    private static void populateDB() throws SQLException {
        String createTable = """
            CREATE TABLE EMPLOYEES (
                id SERIAL PRIMARY KEY ,
                first_name VARCHAR(50),
                last_name VARCHAR(50),
                salary DECIMAL(10, 2)
            );
            """;
        PreparedStatement preparedStatement = connection.prepareStatement(createTable);
        preparedStatement.execute();
    }

    private static void destroyDB() throws SQLException {
        String destroy = """
              DROP table IF EXISTS EMPLOYEES;
            """;
        connection.prepareStatement(destroy)
            .execute();
    }
}
