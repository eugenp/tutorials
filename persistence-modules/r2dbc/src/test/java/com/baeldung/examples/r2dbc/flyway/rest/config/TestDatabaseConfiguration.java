package com.baeldung.examples.r2dbc.flyway.rest.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;

import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;

@TestConfiguration
@EnableR2dbcRepositories
@Profile("test")
public class TestDatabaseConfiguration extends AbstractR2dbcConfiguration {

    @Value("${spring.r2dbc.database}")
    private String database;
    @Value("${spring.r2dbc.username}")
    private String username;
    @Value("${spring.r2dbc.password}")
    private String password;

    @Value("${spring.flyway.locations}")
    private String locations;

    @Value("${spring.flyway.url}")
    private String url;

    @Override
    @Primary
    @Bean
    public ConnectionFactory connectionFactory() {
        return H2ConnectionFactory.inMemory(database, username, password);
    }

    @Bean
    @Primary
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        return initializer;
    }

    @Bean(initMethod = "migrate")
    @Primary
    public Flyway flyway() {
        return new Flyway(Flyway.configure()
          .dataSource(url, username, password)
          .locations(locations)
          .baselineOnMigrate(true));
    }

}