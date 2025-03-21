package com.baeldung.encoding.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.baeldung.encoding.UriEncodingInterceptor;

@Configuration
public class RestTemplateWithInterceptorsConfig {
    @Qualifier("restTemplateWithInterceptors")
    @Bean
    public RestTemplate restTemplateWithInterceptors() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new UriEncodingInterceptor()));
        return restTemplate;
    }
}
