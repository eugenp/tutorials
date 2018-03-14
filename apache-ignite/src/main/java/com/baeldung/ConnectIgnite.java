package com.baeldung;

import java.sql.*;

/**
 * Created by Gebruiker on 3/14/2018.
 */
public class ConnectIgnite {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Register JDBC driver.
        Class.forName("org.apache.ignite.IgniteJdbcThinDriver");

        // Open JDBC connection.
        Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");
        // Create database tables.
        createDatabaseTables(conn);

        insertData(conn);

        getData(conn);
    }

    private static void insertData(Connection conn) throws SQLException {
        // Populate City table
        try (PreparedStatement stmt =
                     conn.prepareStatement("INSERT INTO City (id, name) VALUES (?, ?)")) {

            stmt.setLong(1, 1L);
            stmt.setString(2, "Forest Hill");
            stmt.executeUpdate();

            stmt.setLong(1, 2L);
            stmt.setString(2, "Denver");
            stmt.executeUpdate();

            stmt.setLong(1, 3L);
            stmt.setString(2, "St. Petersburg");
            stmt.executeUpdate();
        }

// Populate Person table
        try (PreparedStatement stmt =
                     conn.prepareStatement("INSERT INTO Person (id, name, city_id) VALUES (?, ?, ?)")) {

            stmt.setLong(1, 1L);
            stmt.setString(2, "John Doe");
            stmt.setLong(3, 3L);
            stmt.executeUpdate();

            stmt.setLong(1, 2L);
            stmt.setString(2, "Jane Roe");
            stmt.setLong(3, 2L);
            stmt.executeUpdate();

            stmt.setLong(1, 3L);
            stmt.setString(2, "Mary Major");
            stmt.setLong(3, 1L);
            stmt.executeUpdate();

            stmt.setLong(1, 4L);
            stmt.setString(2, "Richard Miles");
            stmt.setLong(3, 2L);
            stmt.executeUpdate();
        }
    }

    private static void createDatabaseTables(Connection conn) throws SQLException {

        try (Statement stmt = conn.createStatement()) {

            // Create table based on REPLICATED template.
            stmt.executeUpdate("CREATE TABLE City (" +
                    " id LONG PRIMARY KEY, name VARCHAR) " +
                    " WITH \"template=replicated\"");

            // Create table based on PARTITIONED template with one backup.
            stmt.executeUpdate("CREATE TABLE Person (" +
                    " id LONG, name VARCHAR, city_id LONG, " +
                    " PRIMARY KEY (id, city_id)) " +
                    " WITH \"backups=1, affinityKey=city_id\"");

            // Create an index on the City table.
            stmt.executeUpdate("CREATE INDEX idx_city_name ON City (name)");

            // Create an index on the Person table.
            stmt.executeUpdate("CREATE INDEX idx_person_name ON Person (name)");
        }
    }

    private static void getData(Connection conn) throws SQLException {
        // Get data
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs =
                         stmt.executeQuery("SELECT p.name, c.name " +
                                 " FROM Person p, City c " +
                                 " WHERE p.city_id = c.id")) {

                while (rs.next())
                    System.out.println(rs.getString(1) + ", " + rs.getString(2));
            }

        }
    }

}
