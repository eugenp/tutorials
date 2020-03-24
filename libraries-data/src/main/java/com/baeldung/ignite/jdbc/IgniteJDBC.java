package com.baeldung.ignite.jdbc;

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

        Statement sql = conn.createStatement();
        sql.executeUpdate("CREATE TABLE Employee (" +
                " id INTEGER PRIMARY KEY, name VARCHAR, isEmployed timyint(1)) " +
                " WITH \"template=replicated\"");

        sql.executeUpdate("CREATE INDEX idx_employee_name ON Employee (name)");
    }

    private static void insertData(Connection conn) throws SQLException {

        PreparedStatement sql =
                conn.prepareStatement("INSERT INTO Employee (id, name, isEmployed) VALUES (?, ?, ?)");
        sql.setLong(1, 1);
        sql.setString(2, "James");
        sql.setBoolean(3, true);
        sql.executeUpdate();

        sql.setLong(1, 2);
        sql.setString(2, "Monica");
        sql.setBoolean(3, false);
        sql.executeUpdate();
    }

    private static void getData(Connection conn) throws SQLException {

        Statement sql = conn.createStatement();
        ResultSet rs = sql.executeQuery("SELECT e.name, e.isEmployed " +
                " FROM Employee e " +
                " WHERE e.isEmployed = TRUE ");

        while (rs.next())
            System.out.println(rs.getString(1) + ", " + rs.getString(2));
    }
}
