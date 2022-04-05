package com.baeldung.jdbcrowset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class DatabaseConfiguration {
 
    
    public static Connection geth2Connection() throws Exception {
        Class.forName("org.h2.Driver");
        System.out.println("Driver Loaded.");
        String url = "jdbc:h2:mem:testdb";
        return DriverManager.getConnection(url, "sa", "");
      }
    
    public static void initDatabase(Statement stmt) throws SQLException{
        int iter = 1;
        while(iter<=5){
            String customer = "Customer"+iter;
            String sql ="INSERT INTO customers(id, name) VALUES ("+iter+ ",'"+customer+"');";
            System.out.println("here is sql statmeent for execution: " + sql);
            stmt.executeUpdate(sql);
            iter++;
        }
        
        int iterb = 1;
        while(iterb<=5){
            String associate = "Associate"+iter;
            String sql = "INSERT INTO associates(id, name) VALUES("+iterb+",'"+associate+"');";
            System.out.println("here is sql statement for associate:"+ sql);
            stmt.executeUpdate(sql);
            iterb++;
        }
        
        
    }
    
    

}
