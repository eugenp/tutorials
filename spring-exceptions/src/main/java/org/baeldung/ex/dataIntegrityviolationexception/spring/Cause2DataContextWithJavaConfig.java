package org.baeldung.ex.dataIntegrityviolationexception.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.baeldung.ex.dataIntegrityviolationexception.cause2")
public class Cause2DataContextWithJavaConfig {

    public Cause2DataContextWithJavaConfig() {
        super();
    }

    // beans

}