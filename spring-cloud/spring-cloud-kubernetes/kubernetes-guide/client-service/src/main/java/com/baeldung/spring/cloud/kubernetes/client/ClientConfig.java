package com.baeldung.spring.cloud.kubernetes.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bean")
public class ClientConfig {

    private String message = "Message from backend is: %s <br/> Services : %s";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}