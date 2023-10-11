package com.baeldung.connectiondetails.configuration;

import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("jdbc")
public class JdbcConnectionDetailsConfiguration {
    @Bean
    @Primary
    public JdbcConnectionDetails getPostgresConnection() {
        return new PostgresConnectionDetails();
    }
}
