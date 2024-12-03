package com.baledung.spring.jdbc.callprocedures;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

@Configuration
public class TestConfiguration {

    Logger logger = LoggerFactory.getLogger(TestConfiguration.class);
    PostgreSQLContainer postgreSQLContainer;
    DataSource dataSource;

    @Bean
    public PostgreSQLContainer postgresContainer() {
        PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:16-alpine");
        postgresContainer.start();
        this.postgreSQLContainer = postgresContainer;
        return postgresContainer;
    }

    @Bean
    @DependsOn({"postgresContainer", "datasource"})
    public JdbcTemplate jdbcTemplate() {

        return new JdbcTemplate(dataSource);
    }

    @Bean
    @DependsOn("postgresContainer")
    public DataSource datasource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(postgreSQLContainer.getDriverClassName());
        dataSource.setUrl(postgreSQLContainer.getJdbcUrl() + "&escapeSyntaxCallMode=callIfNoReturn");
        dataSource.setUsername(postgreSQLContainer.getUsername());
        dataSource.setPassword(postgreSQLContainer.getPassword());
        logger.info("driver {}, jdbcurl: {}, user: {}, password: {}",
            postgreSQLContainer.getDriverClassName(), postgreSQLContainer.getJdbcUrl(),
            postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
        this.dataSource = dataSource;
        return dataSource;
    }
}
