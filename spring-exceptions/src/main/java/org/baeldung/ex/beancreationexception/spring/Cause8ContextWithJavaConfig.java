package org.baeldung.ex.beancreationexception.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.baeldung.ex.beancreationexception.cause8")
public class Cause8ContextWithJavaConfig {

    public Cause8ContextWithJavaConfig() {
        super();
    }

    // beans

}