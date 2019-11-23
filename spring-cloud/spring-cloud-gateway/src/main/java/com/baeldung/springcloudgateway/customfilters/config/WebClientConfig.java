package com.baeldung.springcloudgateway.customfilters.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient client() {
        return WebClient.builder()
            .build();
    }

}
