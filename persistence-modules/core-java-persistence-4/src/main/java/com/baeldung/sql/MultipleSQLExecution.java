package com.baeldung.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
}