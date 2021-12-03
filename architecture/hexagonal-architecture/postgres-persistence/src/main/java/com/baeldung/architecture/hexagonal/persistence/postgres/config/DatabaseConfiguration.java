package com.baeldung.architecture.hexagonal.persistence.postgres.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@PropertySources({ @PropertySource("classpath:postgres-persistence.properties"), @PropertySource("classpath:application.properties") })
public class DatabaseConfiguration {
}
