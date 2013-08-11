package org.baeldung.ex.beancreationexception.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.baeldung.ex.beancreationexception.cause6")
public class Cause6ContextWithJavaConfig {

    public Cause6ContextWithJavaConfig() {
        super();
    }

    // beans

}