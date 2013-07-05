package org.baeldung.ex.nosuchbeandefinitionexception.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.baeldung.di.cause3")
public class Cause3ContextWithJavaConfig {

    public Cause3ContextWithJavaConfig() {
        super();
    }

    // beans

}