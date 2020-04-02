package com.baeldung.acl.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.baeldung.acl.persistence.dao")
@PropertySource("classpath:com.baeldung.acl.datasource.properties")
@EntityScan(basePackages={ "com.baeldung.acl.persistence.entity" })
public class JPAPersistenceConfig {
    
}
