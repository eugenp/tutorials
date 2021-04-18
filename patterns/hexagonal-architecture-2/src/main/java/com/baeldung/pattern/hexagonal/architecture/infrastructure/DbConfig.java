package com.baeldung.pattern.hexagonal.architecture.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.baeldung.pattern.hexagonal.architecture.infrastructure.adapters")
public class DbConfig {

}
