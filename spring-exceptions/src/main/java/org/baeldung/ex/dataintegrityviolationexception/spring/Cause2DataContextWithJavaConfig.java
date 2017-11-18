package org.baeldung.ex.dataintegrityviolationexception.spring;

import org.baeldung.spring.config.PersistenceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("org.baeldung.ex.dataIntegrityviolationexception.cause2")
@Import(PersistenceConfig.class)
public class Cause2DataContextWithJavaConfig {

    public Cause2DataContextWithJavaConfig() {
        super();
    }

    // beans

}