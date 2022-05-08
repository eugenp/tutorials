package com.baeldung.security.opa.config;

import javax.annotation.Nonnull;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "opa")
@Data
public class OpaProperties {
    @Nonnull
    private String endpoint = "http://localhost:8181";
}
