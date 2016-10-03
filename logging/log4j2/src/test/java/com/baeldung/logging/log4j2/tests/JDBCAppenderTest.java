package com.baeldung.logging.log4j2.tests;

import com.baeldung.logging.log4j2.tests.jdbc.ConnectionFactory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.junit.LoggerContextRule;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Connection;
import java.sql.ResultSet;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class JDBCAppenderTest {

    @Rule
    public LoggerContextRule contextRule = new LoggerContextRule("log4j2-jdbc-appender.xml");

    @BeforeClass
    public static void setup() throws Exception {
        Connection connection = ConnectionFactory.getConnection();
        connection.createStatement()
          .execute("CREATE TABLE logs(" +
            "when TIMESTAMP," +
            "logger VARCHAR(255)," +
            "level VARCHAR(255)," +
            "message VARCHAR(4096)," +
            "throwable TEXT)");
        //connection.commit();
    }

    @Test
    public void givenLoggerWithJdbcConfig_shouldLogToDataSource() throws Exception {
        Logger logger = contextRule.getLogger(getClass().getSimpleName());
        final int count = 88;
        for (int i = 0; i < count; i++) {
            logger.info("This is JDBC message #{} at INFO level.", count);
        }
        Connection connection = ConnectionFactory.getConnection();
        ResultSet resultSet = connection.createStatement()
          .executeQuery("SELECT COUNT(*) AS ROW_COUNT FROM logs");
        int logCount = 0;
        if (resultSet.next()) {
            logCount = resultSet.getInt("ROW_COUNT");
        }
        assertTrue(logCount == count);
    }
}
