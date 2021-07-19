package com.baeldung.spring.cloud.aws.rds;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 
 * To run this Live Test, we need to have an AWS account and have API keys generated for programmatic access.
 * 
 * Check the README file in this module for more information.
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringCloudRDSLiveTest {

    @Autowired
    DataSource dataSource;

    @Test
    public void whenDataSourceCreated_thenSuccess() {
       assertThat(dataSource).isNotNull();
    }

    @Test
    public void givenDataSource_whenConnectionCreated_thenSuccess() throws SQLException {
        Connection connection = dataSource.getConnection();
        assertThat(connection).isNotNull();
    }

    @Test
    public void givenConnection_whenQueryExecuted_thenSuccess() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT 1");
        while (resultSet.next()) {
            int result = resultSet.getInt(1);
            assertThat(result).isEqualTo(1);
        }
        connection.close();
    }
}
