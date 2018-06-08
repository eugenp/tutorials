package com.baeldung.shutdown;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.baeldung.shutdown")
public class ShutdownConfig {

    @Bean
    public TerminateBean getTerminateBean() {
        return new TerminateBean();
    }
}
