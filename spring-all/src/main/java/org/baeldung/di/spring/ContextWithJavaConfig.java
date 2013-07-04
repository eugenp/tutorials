package org.baeldung.di.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.baeldung.di")
public class ContextWithJavaConfig {

    public ContextWithJavaConfig() {
        super();
    }

    // beans

}