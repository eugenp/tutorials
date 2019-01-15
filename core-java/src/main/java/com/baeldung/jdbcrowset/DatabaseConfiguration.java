package com.baeldung.jdbcrowset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import org.h2.engine.ConnectionInfo;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @author zn.wang
 */
@Configuration
@EnableAutoConfiguration
public class DatabaseConfiguration {

    /**
     * 默认数据库：jdbc:h2:mem:testdb
     *
     * 注意一：为啥写mem
     * 为什么这么写，请看代码 {@link org.h2.engine.ConnectionInfo#ConnectionInfo(String, Properties)}
     * 代码中的parseName()方法的实现。
     *
     * 注意二：为啥写testdb
     * 为什么这么写，请看代码 {@link org.h2.engine.SessionRemote#connectEmbeddedOrServer(boolean)}
     * 代码中的sessionFactory.createSession(ci)方法的实现（
     * {@link org.h2.engine.Engine#createSession(ConnectionInfo)}
     * -> {@link org.h2.engine.Engine#createSessionAndValidate(ConnectionInfo)}
     * -> {@link org.h2.engine.Engine#openSession(ConnectionInfo)}
     * -> {@link org.h2.engine.Engine#openSession(ConnectionInfo, boolean, String)}中的66行代码
     * ）。
     *testdb
     * 注意三：databaseName默认为：mem:testdb  databaseShortName：TESTDB
     *
     *
     * @return
     * @throws Exception
     */
    public static Connection geth2Connection() throws Exception {
        Class.forName("org.h2.Driver");
        System.out.println("Driver Loaded.");
        String url = "jdbc:h2:mem:testdb";
        return DriverManager.getConnection(url, "sa", "");
      }

    /**
     * 初始化2张表：
     * （1）customers（id, name）
     * （2）associates（id, name）
     * @param stmt
     * @throws SQLException
     */
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
