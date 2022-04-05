package com.baeldung.properties.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:foo.properties")
public class BasicPropertiesWithJavaConfig {

    public BasicPropertiesWithJavaConfig() {
        super();
    }

}