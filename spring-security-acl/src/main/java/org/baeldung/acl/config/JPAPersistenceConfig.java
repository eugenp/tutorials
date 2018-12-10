package org.baeldung.acl.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.baeldung.acl.persistence.dao")
@PropertySource("classpath:org.baeldung.acl.datasource.properties")
@EntityScan(basePackages={ "org.baeldung.acl.persistence.entity" })
public class JPAPersistenceConfig {
    
}
