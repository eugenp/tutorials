package com.baeldung;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@TestConfiguration
public class ConfigIntegrationTest {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
