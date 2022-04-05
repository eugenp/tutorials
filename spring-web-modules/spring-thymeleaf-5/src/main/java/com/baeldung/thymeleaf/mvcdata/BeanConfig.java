package com.baeldung.thymeleaf.mvcdata;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.thymeleaf.mvcdata.repository.EmailData;

@Configuration
public class BeanConfig {
    @Bean
    public EmailData emailData() {
        return new EmailData();
    }
}
