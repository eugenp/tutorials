package com.baeldung.countingbeans.olderspring.factorybeans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfigurationBean {

    @Bean
    @MyCustomAnnotation
    MyService myService() {
        return new MyService();
    }

}
