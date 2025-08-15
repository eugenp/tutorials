package com.java.baeldung.spring.test;

import com.github.seregamorph.testsmartcontext.jdbc.LateInitDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.postgresql.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@Configuration
public class DataSourceTestConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceTestConfiguration.class);

// Avoid creating TestContainer objects which are not spring-managed beans
//    @Bean
//    public DataSource dataSource() {
//        // not a manageable bean!
//        var container = new PostgreSQLContainer("postgres:9.6");
//        container.start();
//        return createDataSource("main", container);
//    }

    @Bean
    public DataSource dataSource(PostgreSQLContainer<?> postgres) {
        return createDataSource("main", postgres);
    }

    private static DataSource createDataSource(String name, PostgreSQLContainer<?> postgres) {
        // todo schema migrations, test data insertion, etc.
        if (postgres.isRunning()) {
            // already running - create direct dataSource
            logger.info("Eagerly initializing pool {}", name);
            return createHikariDataSourceForContainer(name, postgres);
        } else {
            // initialize lazily on first getConnection
            logger.info("Pool {} will be initialized lazily", name);
            return new LateInitDataSource(name, () -> {
                logger.info("Starting container for pool {}", name);
                postgres.start();
                return createHikariDataSourceForContainer(name, postgres);
            });
        }
    }

    private static HikariDataSource createHikariDataSourceForContainer(String name, PostgreSQLContainer<?> container) {
        var hikariDataSource = new HikariDataSource();
        hikariDataSource.setUsername(container.getUsername());
        hikariDataSource.setPassword(container.getPassword());
        hikariDataSource.setMinimumIdle(0);
        hikariDataSource.setMaximumPoolSize(50);
        hikariDataSource.setIdleTimeout(10000);
        hikariDataSource.setConnectionTimeout(10000);
        hikariDataSource.setAutoCommit(true);
        hikariDataSource.setPoolName(name);
        hikariDataSource.setDriverClassName(Driver.class.getName());
        hikariDataSource.setJdbcUrl(container.getJdbcUrl());
        return hikariDataSource;
    }
}
