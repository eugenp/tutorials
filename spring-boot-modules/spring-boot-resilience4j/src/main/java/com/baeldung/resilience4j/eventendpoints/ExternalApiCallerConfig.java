package com.baeldung.resilience4j.eventendpoints;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ExternalApiCallerConfig {
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplateBuilder().rootUri("http://localhost:9090").build();
  }
}
