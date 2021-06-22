package com.baeldung.hexagonalarchitecture2;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.List;
import java.util.Map;

public class IntegrationTestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:12").withReuse(true);

    public static Map<String, Object> getProperties() {
        return Map.of("spring.datasource.url", postgres.getJdbcUrl(), "spring.datasource.username", postgres.getUsername(), "spring.datasource.password", postgres.getPassword());
    }

    public static PostgreSQLContainer<?> getPostgres() {
        return postgres;
    }

    public static List<? extends GenericContainer<?>> getContainers() {
        return List.of(postgres);
    }

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        Startables.deepStart(getContainers()).join();
        var env = context.getEnvironment();
        env.getPropertySources().addFirst(new MapPropertySource("containers", getProperties()));
    }
}
