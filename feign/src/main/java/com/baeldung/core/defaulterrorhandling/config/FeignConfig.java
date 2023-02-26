package com.baeldung.core.defaulterrorhandling.config;

import org.springframework.context.annotation.Bean;

import feign.Logger;

public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}