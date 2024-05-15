package com.baeldung.ex.beancreationexception.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan("com.baeldung.ex.beancreationexception.cause4")
@ImportResource("classpath:beancreationexception_cause4.xml")
public class Cause4ContextWithJavaConfig {

    public Cause4ContextWithJavaConfig() {
        super();
    }

    // beans

}