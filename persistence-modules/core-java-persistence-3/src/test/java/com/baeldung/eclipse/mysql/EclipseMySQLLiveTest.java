package com.baeldung.eclipse.mysql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EclipseMySQLLiveTest {

    private static Connection conn;
    private static Statement stmt;

    @BeforeAll
    public static void setUp() throws Exception {
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/sampledb";
        String user = "root";

        //Replace this password with your own DB Password
        String password = "yourdbpassword";

        // Load the MySQL Connector/J driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish connection
        conn = DriverManager.getConnection(url, user, password);
        stmt = conn.createStatement();
    }

    @AfterAll
    public void tearDown() throws Exception {
        // Close the connection
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    @Test
    public void givenNewUser_whenInsertingUser_thenUserIsInsertedAndRetrieved() throws Exception {
        // Insert a new user
        String insertSQL = "INSERT INTO users (username, email) VALUES ('john_doe', 'john.doe@example.com')";
        stmt.executeUpdate(insertSQL);

        // Execute a query to retrieve the inserted data
        ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username = 'john_doe'");

        // Verify the result
        assertTrue(rs.next());
        assertEquals("john_doe", rs.getString("username"));
        assertEquals("john.doe@example.com", rs.getString("email"));

        // Clean up by deleting the inserted user
        String deleteSQL = "DELETE FROM users WHERE username = 'john_doe'";
        stmt.executeUpdate(deleteSQL);
    }
}
