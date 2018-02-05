package com.baeldung.springdata;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.baeldung.model" }, transactionManagerRef = "jpaTransactionManager")
@EnableJpaAuditing
@PropertySource({ "classpath:hibernate.properties" })
@ComponentScan({ "com.baeldung.model" })
public class PersistenceConfig {
    
}