package com.baeldung.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultipleSQLExecution {
    
    private final Logger logger = LoggerFactory.getLogger(MultipleSQLExecution.class);

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
    
    public boolean executeMultipleSelectStatements() throws SQLException {
        String sql = "SELECT * FROM users WHERE email = 'alice@example.com';" +
                     "SELECT * FROM users WHERE email = 'bob@example.com';";

        try (Statement statement = connection.createStatement()) {
            boolean hasResultSet = statement.execute(sql);
            // We'll log each record using this loop
            do {
                if (hasResultSet) {
                    try (ResultSet resultSet = statement.getResultSet()) {
                        while (resultSet.next()) {
                            logger.info("User ID: " + resultSet.getInt("id"));
                            logger.info("Name: " + resultSet.getString("name"));
                            logger.info("Email: " + resultSet.getString("email"));
                        }
                    }
                } else {
                    // Here we don't have any update statements. 
                    // However, if SQL contains update statements the we need to handle update counts gracefully
                    int updateCount = statement.getUpdateCount();
                    if (updateCount == -1) {
                        logger.info("No update counts for this statement.");
                    }
                }
            } while (statement.getMoreResults() || statement.getUpdateCount() != -1);

            return true;
        }
    }
}