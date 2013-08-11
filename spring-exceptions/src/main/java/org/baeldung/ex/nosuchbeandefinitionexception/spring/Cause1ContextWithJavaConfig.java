package org.baeldung.ex.nosuchbeandefinitionexception.spring;

import org.baeldung.persistence.spring.PersistenceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("org.baeldung.ex.nosuchbeandefinitionexception.cause1")
@Import(PersistenceConfig.class)
public class Cause1ContextWithJavaConfig {

    public Cause1ContextWithJavaConfig() {
        super();
    }

    // beans

}