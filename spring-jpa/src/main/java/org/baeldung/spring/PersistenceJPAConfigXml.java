package org.baeldung.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// @Configuration
@EnableTransactionManagement
@ComponentScan({ "org.baeldung.persistence" })
@ImportResource({ "classpath:jpaConfig.xml" })
public class PersistenceJPAConfigXml {

    public PersistenceJPAConfigXml() {
        super();
    }

}