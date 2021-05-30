package com.shaheen.hexa.database.h2.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.shaheen.hexa.database.h2")
@EntityScan(basePackages = "com.shaheen.hexa.database.h2")
public class SqlDatabaseConfiguration {
}
