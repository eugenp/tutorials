package com.java.baeldung.spring.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
public class PostgresTestConfiguration {

    private static PostgreSQLContainer<?> postgres;

    // override destroy method to empty to avoid closing docker container
    // bean on closing spring context
    @Bean(destroyMethod = "")
    public PostgreSQLContainer<?> postgresContainer() {
        synchronized (PostgresTestConfiguration.class) {
            if (postgres == null) {
                postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14")
                        .asCompatibleSubstituteFor("postgres"));
            }
            return postgres;
        }
    }
}
