package com.baeldung;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;

@Configuration
public class OtelConfiguration {
    @Bean
    public OpenTelemetry openTelemetry() {
        return GlobalOpenTelemetry.get();
    }
}
