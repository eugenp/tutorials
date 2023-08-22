package com.baeldung.script;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyBatisScriptUtilityUnitTest {
    private static final Log logger = LogFactory.getLog(MyBatisScriptUtilityUnitTest.class);
    private static Connection connection = null;
    private static final String JDBC_URL = "jdbc:h2:mem:testdb1";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    @Before
    public void prepareConnection() throws Exception {
        connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    @AfterAll
    public static void closeConnection() throws Exception {
        connection.close();
    }

    @Test
    public void givenConnectionObject_whenSQLFile_thenExecute() throws Exception {
        String path = new File(ClassLoader.getSystemClassLoader()
                .getResource("employee.sql").getFile()).toPath().toString();
        MyBatisScriptUtility.runScript(path, connection);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(1) FROM employees");
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            Assert.assertEquals("Incorrect number of records inserted", 20, count);
        }
    }
}
