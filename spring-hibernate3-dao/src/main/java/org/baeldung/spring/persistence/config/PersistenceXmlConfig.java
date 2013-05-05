package org.baeldung.spring.persistence.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "org.baeldung.spring.persistence.dao", "org.baeldung.spring.persistence.service" })
@ImportResource({ "classpath:persistenceConfig.xml" })
public class PersistenceXmlConfig {

    public PersistenceXmlConfig() {
        super();
    }

}