package com.baeldung.hexagonal.store.infrastructure.persistence;

import com.baeldung.hexagonal.store.infrastructure.persistence.config.EntityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.baeldung.hexagonal.store.infrastructure.persistence")
@Import({EntityConfig.class})
@ComponentScan(basePackages = "com.baeldung.hexagonal.store.infrastructure.persistence")
public class PersistenceConfig {
}
