package com.baeldung.connectionpool.connectionpools;

import java.sql.Connection;
import java.util.List;

public interface ConnectionPool {

    Connection getConnection(int id);
    
    void releaseConnection(Connection connection);
    
    List<Connection> getConnectionPool();
    
    String getUrl();
    
    String getUser();

    String getPassword();
}   