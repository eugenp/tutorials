package com.baeldung.springcloudgateway.webfilters.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;

@Configuration
public class RequestRateLimiterResolverConfig {
    
    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.just("1");
    }
}
