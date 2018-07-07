package org.baeldung.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// @Configuration
@EnableTransactionManagement
@ComponentScan({ "org.baeldung.persistence.dao", "org.baeldung.persistence.service" })
@ImportResource({ "classpath:persistenceConfig.xml" })
public class PersistenceXmlConfig {

    public PersistenceXmlConfig() {
        super();
    }

}