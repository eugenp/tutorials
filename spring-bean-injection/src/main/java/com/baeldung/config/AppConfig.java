package com.baeldung.config;

import com.baeldung.service.TestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public TestService TestService(){
        return new TestService();
    }

}
