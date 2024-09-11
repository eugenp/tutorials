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

import com.baeldung.TestHelper;

public class GetInsertIdsUnitTest extends TestHelper {
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
}
