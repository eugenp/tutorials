package org.baeldung.ex.dataIntegrityviolationexception.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.baeldung.ex.dataIntegrityviolationexception.cause3")
public class Cause3DataContextWithJavaConfig {

    public Cause3DataContextWithJavaConfig() {
        super();
    }

    // beans

}