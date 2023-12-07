package com.baeldung.connectiondetails.configuration;

import org.springframework.boot.actuate.autoconfigure.tracing.zipkin.ZipkinConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("zipkin")
public class CustomZipkinConnectionDetailsConfiguration {
    @Bean
    @Primary
    public ZipkinConnectionDetails getZipkinConnectionDetails() {
        return new CustomZipkinConnectionDetails();
    }
}
