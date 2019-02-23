package com.baeldung.spring.cloud.kubernetes.services.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "client")
public class ClientConfig {

    private String type = "generic-client";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
