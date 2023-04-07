package com.baeldung.customauth.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${api.auth.header.name}")
    private String apiAuthHeaderName;

    @Value("${api.auth.secret}")
    private String apiAuthHeaderSecret;

    public String getApiAuthHeaderName() {
        return apiAuthHeaderName;
    }

    public String getApiAuthHeaderSecret() {
        return apiAuthHeaderSecret;
    }
}
