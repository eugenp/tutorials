package com.baeldung.resultset;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataPreparer {

    private static final String SELECT_SQL = "SELECT id, name, category, price FROM products ";

    private Connection connection = null;

    public DataPreparer(Connection connection) {
        this.connection = connection;
    }

    private void prepareTable() throws SQLException {
        String createSql = "CREATE TABLE products (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "category VARCHAR(255), " +
                "price DECIMAL(10, 2) )";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createSql);
        }
    }

    private void prepareData() throws SQLException {
        String insertSql = "INSERT INTO products(name, category, price) " +
                "VALUES ('%s', '%s', %s) ";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format(insertSql, "Chocolate", "Confectionery", "2.99"));
            statement.executeUpdate(String.format(insertSql, "Fruit Jellies", "Confectionery", "1.5"));
            statement.executeUpdate(String.format(insertSql, "Crisps", "Snacks", "1.69"));
            statement.executeUpdate(String.format(insertSql, "Walnuts", "Snacks", "5.95"));
            statement.executeUpdate(String.format(insertSql, "Orange Juice", "Juices", "2.19"));
        }
    }

    public void prepare() {
        try {
            prepareTable();
            prepareData();
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public String getSelectSql() {
        return SELECT_SQL;
    }

}
