package com.baeldung.security.opa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.annotation.Nonnull;
import lombok.Data;

@ConfigurationProperties(prefix = "opa")
@Data
public class OpaProperties {

    @Nonnull
    private String endpoint = "http://localhost:8181";
}
