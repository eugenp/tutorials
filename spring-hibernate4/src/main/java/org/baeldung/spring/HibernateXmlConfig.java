package org.baeldung.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "org.baeldung.spring.persistence.dao", "org.baeldung.spring.persistence.service" })
@ImportResource({ "classpath:hibernate4Config.xml" })
public class HibernateXmlConfig {

    public HibernateXmlConfig() {
        super();
    }

}