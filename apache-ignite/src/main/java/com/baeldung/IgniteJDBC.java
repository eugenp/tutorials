package com.baeldung;

import java.sql.*;

/**
 * Created by Gebruiker on 3/14/2018.
 */
public class IgniteJDBC {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("org.apache.ignite.IgniteJdbcThinDriver");

        Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");

        createDatabaseTables(conn);

        insertData(conn);

        getData(conn);
    }

    private static void createDatabaseTables(Connection conn) throws SQLException {

        try (Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE TABLE Employee (" +
                    " id INTEGER PRIMARY KEY, name VARCHAR, isEmployed timyint(1)) " +
                    " WITH \"template=replicated\"");

            stmt.executeUpdate("CREATE INDEX idx_employee_name ON Employee (name)");
        }
    }

    private static void insertData(Connection conn) throws SQLException {

        try (PreparedStatement stmt =
                     conn.prepareStatement("INSERT INTO Employee (id, name, isEmployed) VALUES (?, ?, ?)")) {

            stmt.setLong(1, 1);
            stmt.setString(2, "James");
            stmt.setBoolean(3, true);
            stmt.executeUpdate();

            stmt.setLong(1, 2);
            stmt.setString(2, "Monica");
            stmt.setBoolean(3, false);
            stmt.executeUpdate();
        }
    }

    private static void getData(Connection conn) throws SQLException {

        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT e.name, e.isEmployed " +
         " FROM Employee e " +
         " WHERE e.isEmployed = TRUE ")) {

                while (rs.next())
                    System.out.println(rs.getString(1) + ", " + rs.getString(2));
            }

        }
    }

}
