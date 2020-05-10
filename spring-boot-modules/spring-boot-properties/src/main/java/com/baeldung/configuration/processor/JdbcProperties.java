package com.baeldung.configuration.processor;

import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.*;

@Configuration
@ConfigurationProperties(prefix = "com.baeldung")
public class JdbcProperties {

    @Value("${jdbc.url:jdbc:postgresql:/localhost:5432}")
    private String jdbcUrl;

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }
}