package org.baeldung.ex.beancreationexception.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan("org.baeldung.ex.beancreationexception.cause7")
@ImportResource("classpath:beancreationexception_cause7.xml")
public class Cause7ContextWithJavaConfig {

    public Cause7ContextWithJavaConfig() {
        super();
    }

    // beans

}