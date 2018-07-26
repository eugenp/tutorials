package com.baeldung.connectionpool.connectionpools;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ConnectionPool {

    Connection getConnection() throws SQLException;
    
    boolean releaseConnection(Connection connection);
    
    String getUrl();
    
    String getUser();

    String getPassword();
}   