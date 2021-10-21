package com.baeldung.derby;

import java.sql.*;

/**
 * Created by arash on 14.10.21.
 */

public class DerbyApplicationDemo {
    public static void main(String[] args) {
        runner("jdbc:derby:baeldung;create=true");
    }

    private static void runner(String urlConnection) {
        try {
            Connection con = DriverManager.getConnection(urlConnection);
            Statement statement = con.createStatement();
            if (!isTableExists("authors", con)) {
                String createSQL = "CREATE TABLE authors (id INT PRIMARY KEY,first_name VARCHAR(255),last_name VARCHAR(255))";
                statement.execute(createSQL);
                String insertSQL = "INSERT INTO authors VALUES (1, 'arash','ariani')";
                statement.execute(insertSQL);
            }
            String selectSQL = "SELECT * FROM authors";
            ResultSet result = statement.executeQuery(selectSQL);
            while (result.next()) {
                // use result here
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isTableExists(String tableName, Connection connection) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet result = meta.getTables(null, null, tableName.toUpperCase(), null);
        return result.next();
    }
}
