package com.baeldung.architecture.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.baeldung.exagonal.architecture")
@EntityScan(("com.baeldung.exagonal.architecture"))
public class DatabaseConfig {
}
