package org.baeldung.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//copied from - spring-security-mvc-basic-auth
@Configuration
@ComponentScan("org.baeldung.persistence")
public class PersistenceConfig {

    public PersistenceConfig() {
        super();
    }

}
