package com.baeldung.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Bean
    public RestClient restClient(SeataXidClientInterceptor seataXidClientInterceptor) {
        return RestClient.builder()
            .requestInterceptor(seataXidClientInterceptor)
            .build();
    }
}
