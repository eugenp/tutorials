package org.baeldung.ex.beancreationexception.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.baeldung.ex.beancreationexception.cause5")
public class Cause5ContextWithJavaConfig {

    public Cause5ContextWithJavaConfig() {
        super();
    }

    // beans

}