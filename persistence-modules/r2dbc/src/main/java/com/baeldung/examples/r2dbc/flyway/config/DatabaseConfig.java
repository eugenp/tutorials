package com.baeldung.examples.r2dbc.flyway.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;

@Configuration
@EnableR2dbcRepositories
@Profile("!test")
class DatabaseConfig extends AbstractR2dbcConfiguration {

    @Value("${spring.r2dbc.host}")
    private String host;
    @Value("${spring.r2dbc.port}")
    private Integer port;
    @Value("${spring.r2dbc.database}")
    private String database;
    @Value("${spring.r2dbc.username}")
    private String username;
    @Value("${spring.r2dbc.password}")
    private String password;

    @Value("${spring.flyway.url}")
    private String url;

    @Value("${spring.flyway.locations}")
    private String locations;

    @Override
    @Bean
    @Profile("!test")
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
          .host(host)
          .port(port)
          .database(database)
          .username(username)
          .password(password)
          .build());
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
          .dataSource(url, username, password)
          .locations(locations)
          .baselineOnMigrate(true)
          .load();
    }
}