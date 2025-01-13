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

    public boolean executeMultipleStatements() {
        String sql = "INSERT INTO users (name, email) VALUES ('Alice', 'alice@example.com');" +
                     "INSERT INTO users (name, email) VALUES ('Bob', 'bob@example.com');";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Multiple statements executed successfully.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int[] executeBatchProcessing() {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);

            statement.addBatch("INSERT INTO users (name, email) VALUES ('Charlie', 'charlie@example.com')");
            statement.addBatch("INSERT INTO users (name, email) VALUES ('Diana', 'diana@example.com')");

            int[] updateCounts = statement.executeBatch();
            connection.commit();

            System.out.println("Batch executed successfully. Update counts: " + updateCounts.length);
            return updateCounts;
        } catch (SQLException e) {
            e.printStackTrace();
            return new int[0];
        }
    }

    public boolean callStoredProcedure() {
        try (CallableStatement callableStatement = connection.prepareCall("{CALL InsertMultipleUsers()}")) {
            callableStatement.execute();
            System.out.println("Stored procedure executed successfully.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}