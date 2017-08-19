package com.baeldung.config;

import com.baeldung.service.MegaTestService;
import com.baeldung.service.SuperTestService;
import com.baeldung.service.TestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public TestService TestService() {
        return new TestService();
    }

    @Bean
    public SuperTestService SuperTestService() {
        return new SuperTestService(new TestService());
    }

    @Bean
    public MegaTestService MegaTestService() {
        return new MegaTestService();
    }
}
