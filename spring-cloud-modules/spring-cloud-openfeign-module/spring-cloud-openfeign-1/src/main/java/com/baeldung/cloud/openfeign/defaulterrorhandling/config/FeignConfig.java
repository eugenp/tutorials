package com.baeldung.cloud.openfeign.defaulterrorhandling.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

//TODO keep
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}