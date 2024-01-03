package com.baeldung.calcite;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalciteUnitTest {

    Logger logger = LoggerFactory.getLogger(CalciteUnitTest.class);

    @Test
    void whenCsvSchema_thenQuerySuccess() throws SQLException {
        Properties info = new Properties();
        info.put("model", getPath("model.json"));
        try(Connection connection = DriverManager.getConnection("jdbc:calcite:", info)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from trades.trade");

            assertEquals(3, resultSet.getMetaData().getColumnCount());

            List<Integer> tradeIds = new ArrayList<>();
            while(resultSet.next()) {
                tradeIds.add(resultSet.getInt("tradeid"));
            }

            assertEquals(3, tradeIds.size());
        }
    }

    private String getPath(String model) {
        URL url = ClassLoader.getSystemClassLoader().getResource(model);
        logger.info("path fetched :" + url.getPath());
        return url.getPath();
    }
}
