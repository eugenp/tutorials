package com.baeldung.reactive.cors.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class CorsGlobalConfiguration implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
            .allowedOrigins("http://allowed-origin.com")
            .allowedMethods("PUT")
            .allowedHeaders("Baeldung-Allowed", "Baledung-Another-Allowed")
            .exposedHeaders("Baeldung-Allowed", "Baeldung-Exposed")
            .maxAge(3600);
    }

}
