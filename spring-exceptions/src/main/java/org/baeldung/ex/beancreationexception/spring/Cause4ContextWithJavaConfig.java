package org.baeldung.ex.beancreationexception.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.baeldung.ex.beancreationexception.cause4")
public class Cause4ContextWithJavaConfig {

    public Cause4ContextWithJavaConfig() {
        super();
    }

    // beans

}