package com.baeldung.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.baeldung.persistence")
@EntityScan("com.baeldung.web")
public class PersistenceConfig {

}
