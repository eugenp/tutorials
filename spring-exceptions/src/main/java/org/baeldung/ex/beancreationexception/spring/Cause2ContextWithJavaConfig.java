package org.baeldung.ex.beancreationexception.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.baeldung.ex.beancreationexception.cause2")
public class Cause2ContextWithJavaConfig {

    public Cause2ContextWithJavaConfig() {
        super();
    }

    // beans

}