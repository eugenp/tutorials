package com.baeldung.dynamically.updating.properties.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class CustomConfig {

    @Bean
    @Scope("prototype")
    public MyService myService(@Value("${custom.property:default}") String property) {
        return new MyService(property);
    }
}
