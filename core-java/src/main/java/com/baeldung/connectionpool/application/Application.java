package com.baeldung.connectionpool.application;

import com.baeldung.connectionpool.connectionpools.BasicConnectionPool;
import com.baeldung.connectionpool.connectionpools.C3poDataSource;
import com.baeldung.connectionpool.connectionpools.ConnectionPool;
import com.baeldung.connectionpool.connectionpools.DBCPDataSource;
import com.baeldung.connectionpool.connectionpools.HikariCPDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    
    public static void main(String[] args) throws SQLException {
        ConnectionPool cp1 = BasicConnectionPool.create("jdbc:h2:mem:test", "user", "password");
        Connection con1 = cp1.getConnection();
        System.out.println(con1);
        cp1.releaseConnection(con1);
        
        Connection con2 = DBCPDataSource.getConnection();
        System.out.println(con2);

        Connection con3 = C3poDataSource.getConnection();
        System.out.println(con3);
        
        Connection con4 = HikariCPDataSource.getConnection();
        System.out.println(con4);
    }
    
}
