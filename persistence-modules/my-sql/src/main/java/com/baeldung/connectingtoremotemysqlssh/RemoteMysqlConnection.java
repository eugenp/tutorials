package com.baeldung.connectingtoremotemysqlssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class RemoteMysqlConnection {

    private static final String HOST = "HOST";
    private static final String USER = "USERNAME";
    private static final String PRIVATE_KEY = "PATH_TO_KEY";
    private static final int PORT = 22;

    private static final String DATABASE_HOST = "DATABASE_HOST";
    private static final int DATABASE_PORT = 3306;
    private static final String DATABASE_USERNAME = "DATABASE_USERNAME";
    private static final String DATABASE_PASSWORD = "DATABASE_PASSWORD";

    public static JSch setUpJsch() throws JSchException {
        JSch jsch = new JSch();
        jsch.addIdentity(PRIVATE_KEY);
        return jsch;
    }

    public static Session createSession(JSch jsch) throws JSchException {
        Session session = jsch.getSession(USER, HOST, PORT);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        return session;
    }

    public static int tunnelNetwork(Session session) throws JSchException {
        int portForwarding = session.setPortForwardingL(0, DATABASE_HOST, DATABASE_PORT);
        return portForwarding;
    }

    public static Connection databaseConnection(int port) throws SQLException {
        String databaseUrl = "jdbc:mysql://" + DATABASE_HOST + ":" + port + "/baeldung";
        Connection connection = DriverManager.getConnection(databaseUrl, DATABASE_USERNAME, DATABASE_PASSWORD);
        return connection;
    }

    public static void createTable(Connection connection, String tableName) throws SQLException {
        String createTableSQL = "CREATE TABLE " + tableName + " (id INT, data VARCHAR(255))";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        }
    }

    public static void insertData(Connection connection, String tableName) throws SQLException {
        String insertDataSQL = "INSERT INTO " + tableName + " (id, data) VALUES (1, 'test data')";
        try (Statement statement = connection.createStatement()) {
            statement.execute(insertDataSQL);
        }
    }

    public static boolean isTableExists(Connection connection, String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SHOW TABLES LIKE '" + tableName + "'");
            return resultSet.next();
        }
    }

    public static void disconnect(Session session, Connection connection) throws SQLException {
        session.disconnect();
        connection.close();
    }
}
