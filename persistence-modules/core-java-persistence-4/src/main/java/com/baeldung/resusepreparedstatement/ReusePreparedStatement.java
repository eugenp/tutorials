package com.baeldung.resusepreparedstatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReusePreparedStatement {

    private Connection connection = null;
    private static final String SQL = "INSERT INTO CUSTOMER (id, first_name, last_name) VALUES(?,?,?)";

    public void setupDatabaseAndConnect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testDB", "dbUser", "dbPassword");
        String createTable = "CREATE TABLE CUSTOMER (id INT, first_name TEXT, last_name TEXT)";
        connection.createStatement()
            .execute(createTable);
    }

    public void destroyDB() throws SQLException {
        String destroy = "DROP table IF EXISTS CUSTOMER";
        connection.prepareStatement(destroy)
            .execute();
        connection.close();
    }

    public void inefficientUsage() throws SQLException {
        for (int i = 0; i < 10000; i++) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1, i);
            preparedStatement.setString(2, "firstname" + i);
            preparedStatement.setString(3, "secondname" + i);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
    }

    public void betterUsage() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            for (int i = 0; i < 10000; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, "firstname" + i);
                preparedStatement.setString(3, "secondname" + i);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void bestUsage() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            connection.setAutoCommit(false);
            for (int i = 0; i < 10000; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, "firstname" + i);
                preparedStatement.setString(3, "secondname" + i);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int checkRowCount() {
        try (PreparedStatement counter = connection.prepareStatement("SELECT COUNT(*) AS customers FROM CUSTOMER")) {
            ResultSet resultSet = counter.executeQuery();
            resultSet.next();
            int count = resultSet.getInt("customers");
            resultSet.close();
            return count;
        } catch (SQLException e) {
            return -1;
        }
    }

}
