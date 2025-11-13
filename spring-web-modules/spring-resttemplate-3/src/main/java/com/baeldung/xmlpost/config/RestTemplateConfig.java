package com.baeldung.xmlpost.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean("xmlRestTemplate")
    public RestTemplate xmlRestTemplate() {
        return new RestTemplate();
    }
}