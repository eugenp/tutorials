package com.baeldung.connectionpool.connectionpools;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class C3poDataSource {

    private static final ComboPooledDataSource cpds = new ComboPooledDataSource();

    static {
        try {
            cpds.setDriverClass("com.mysql.jdbc.Driver");
            cpds.setJdbcUrl("jdbc:mysql://localhost:3306/mydb");
            cpds.setUser("user");
            cpds.setPassword("password");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }
    
    private C3poDataSource(){}
}
