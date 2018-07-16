package com.baeldung.connectionpool.application;

import com.baeldung.connectionpool.connectionpools.BasicConnectionPool;
import com.baeldung.connectionpool.connectionpools.C3poDataSource;
import com.baeldung.connectionpool.connectionpools.ConnectionPool;
import com.baeldung.connectionpool.connectionpools.DBCPDataSource;
import com.baeldung.connectionpool.connectionpools.HirakiDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Application {
    
    public static void main(String[] args) throws SQLException {
        ConnectionPool cp1 = BasicConnectionPool.createFromDriverManager("jdbc:mysql://localhost:3306/mydb", "user", "password");
        Connection con1 = cp1.getConnection(0);
        System.out.println(con1);
        cp1.releaseConnection(con1);

        ConnectionPool cp2 = BasicConnectionPool.createFromMysqlDataSource("jdbc:mysql://localhost:3306/mydb", "user", "password");
        Connection con2 = cp2.getConnection(0);
        System.out.println(con2);
        cp2.releaseConnection(con2);
        
        Connection con3 = DBCPDataSource.getConnection();
        System.out.println(con3);

        Connection con4 = C3poDataSource.getConnection();
        System.out.println(con4);
        
        Connection con5 = HirakiDataSource.getConnection();
        System.out.println(con5);
    }
}