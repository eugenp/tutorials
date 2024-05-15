package com.baeldung.cloud.openfeign.customizederrorhandling.config;

import org.springframework.context.annotation.Bean;

import feign.Logger;
import feign.codec.ErrorDecoder;

public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}