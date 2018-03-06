package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by lihongjie on 5/19/17.
 */
public class DababaseCreator {

    public static void main(String args[]) throws Exception {
        String url = "jdbc:mysql://localhost/shard1";
        String dbusr = "root";
        String dbpwd = "";
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, dbusr, dbpwd);
        Statement statement = connection.createStatement();
        String query;
        query = "drop table s_usr";
        statement.executeUpdate(query);
        query = "create table s_usr(usr_kid integer primary key, usr_name varchar(50)," +
                "usr_gender varchar(10),usr_country varchar(20))";
        statement.executeUpdate(query);
        statement.close();
        connection.close();
        url = "jdbc:mysql://localhost/shard2";
        connection = DriverManager.getConnection(url, dbusr, dbpwd);
        statement = connection.createStatement();
        query = "drop table s_usr";
        statement.executeUpdate(query);
        query = "create table s_usr(usr_kid integer primary key, usr_name varchar(50)," +
                "usr_gender varchar(10),usr_country varchar(20))";
        statement.executeUpdate(query);
        statement.close();
        connection.close();
    }
}
