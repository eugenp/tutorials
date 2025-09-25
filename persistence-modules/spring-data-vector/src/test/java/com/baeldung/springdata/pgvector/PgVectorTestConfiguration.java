package com.baeldung.springdata.pgvector;


import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
@Profile("pgvector")
public class PgVectorTestConfiguration {
    private final Logger logger = LoggerFactory.getLogger(PgVectorTestConfiguration.class);

    private PostgreSQLContainer pgVectorSQLContainer;
    private DataSource dataSource;

    @Bean
    public PostgreSQLContainer pgVectorSQLContainer() {
        PostgreSQLContainer<?> pgVector = new PostgreSQLContainer<>(
            DockerImageName.parse("pgvector/pgvector:pg16")
                .asCompatibleSubstituteFor("postgres")
        );
        pgVector.start();
        this.pgVectorSQLContainer = pgVector;
        return pgVector;
    }

    @Bean
    @DependsOn({"pgVectorSQLContainer", "datasource"})
    public JdbcTemplate jdbcTemplate() {

        return new JdbcTemplate(dataSource);
    }

    @Bean
    @DependsOn("pgVectorSQLContainer")
    public DataSource datasource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(pgVectorSQLContainer.getDriverClassName());
        dataSource.setUrl(pgVectorSQLContainer.getJdbcUrl());
        dataSource.setUsername(pgVectorSQLContainer.getUsername());
        dataSource.setPassword(pgVectorSQLContainer.getPassword());
        logger.info("driver {}, jdbcurl: {}, user: {}, password: {}",
            pgVectorSQLContainer.getDriverClassName(), pgVectorSQLContainer.getJdbcUrl(),
            pgVectorSQLContainer.getUsername(), pgVectorSQLContainer.getPassword());
        this.dataSource = dataSource;
        return dataSource;
    }
}
