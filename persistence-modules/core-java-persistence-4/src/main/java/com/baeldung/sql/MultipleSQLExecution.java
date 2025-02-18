package com.baeldung.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MultipleSQLExecution {

    private Connection connection;

    public MultipleSQLExecution(Connection connection) {
        this.connection = connection;
    }

    public boolean executeMultipleStatements() throws SQLException {
        String sql = "INSERT INTO users (name, email) VALUES ('Alice', 'alice@example.com');" +
                     "INSERT INTO users (name, email) VALUES ('Bob', 'bob@example.com');";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            return true;
        }
    }

    public int[] executeBatchProcessing() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);

            statement.addBatch("INSERT INTO users (name, email) VALUES ('Charlie', 'charlie@example.com')");
            statement.addBatch("INSERT INTO users (name, email) VALUES ('Diana', 'diana@example.com')");

            int[] updateCounts = statement.executeBatch();
            connection.commit();

            return updateCounts;
        }
    }

    public boolean callStoredProcedure() throws SQLException {
        try (CallableStatement callableStatement = connection.prepareCall("{CALL InsertMultipleUsers()}")) {
            callableStatement.execute();
            return true;
        }
    }
    
    public List<User> executeMultipleSelectStatements() throws SQLException {
        String sql = "SELECT * FROM users WHERE email = 'alice@example.com';" +
                     "SELECT * FROM users WHERE email = 'bob@example.com';";

        List<User> users = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql); // Here we execute the multiple queries

            do {
                try (ResultSet resultSet = statement.getResultSet()) {
                    while (resultSet != null && resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String email = resultSet.getString("email");
                        users.add(new User(id, name, email));
                    }
                }
            } while (statement.getMoreResults());

        }
        return users;
    }
}