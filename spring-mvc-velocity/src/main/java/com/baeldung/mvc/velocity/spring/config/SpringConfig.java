package com.baeldung.mvc.velocity.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.mvc.velocity.service.TutorialsService;

@Configuration public class SpringConfig {

    @Bean public TutorialsService tutService() {
        return new TutorialsService();
    }
}
