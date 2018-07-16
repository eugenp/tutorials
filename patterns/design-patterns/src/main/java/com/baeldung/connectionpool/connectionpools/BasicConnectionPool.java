package com.baeldung.connectionpool.connectionpools;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasicConnectionPool implements ConnectionPool {

    private final String url;
    private final String user;
    private final String password;
    private final List<Connection> connectionPool;
    private static final int MAX_CONNECTIONS = 10;
    
    public static BasicConnectionPool createFromDriverManager(String url, String user, String password) throws SQLException {
        List<Connection> pool = new ArrayList<>(MAX_CONNECTIONS);
        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            pool.add(DriverManager.getConnection(url, user, password));
        }
        return new BasicConnectionPool(url, user, password, pool);
    }
    
    private BasicConnectionPool(String url, String user, String password, List<Connection> connectionPool) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connectionPool = connectionPool;
    }

    @Override
    public Connection getConnection(int id) {
        if (id < 0 || id > MAX_CONNECTIONS) {
            throw new RuntimeException();
        }
        return connectionPool.get(id);
    }

    @Override
    public void releaseConnection(Connection connection) {
        connectionPool.add(connection);
    }

    @Override
    public List<Connection> getConnectionPool() {
        return connectionPool;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
