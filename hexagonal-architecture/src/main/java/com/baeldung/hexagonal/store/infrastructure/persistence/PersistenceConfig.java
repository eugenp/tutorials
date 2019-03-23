package com.baeldung.hexagonal.store.infrastructure.persistence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.baeldung.hexagonal.store.infrastructure.persistence")
@EntityScan(basePackages = {"com.baeldung.hexagonal.store.core"})
@ComponentScan(basePackages = {"com.baeldung.hexagonal.store.infrastructure.persistence", "com.baeldung.hexagonal.store.infrastructure.emailsender"})
public class PersistenceConfig {
}
