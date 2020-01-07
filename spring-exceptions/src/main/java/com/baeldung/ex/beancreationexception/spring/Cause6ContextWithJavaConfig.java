package com.baeldung.ex.beancreationexception.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan("com.baeldung.ex.beancreationexception.cause6")
@ImportResource("classpath:beancreationexception_cause6.xml")
public class Cause6ContextWithJavaConfig {

    public Cause6ContextWithJavaConfig() {
        super();
    }

    // beans

}