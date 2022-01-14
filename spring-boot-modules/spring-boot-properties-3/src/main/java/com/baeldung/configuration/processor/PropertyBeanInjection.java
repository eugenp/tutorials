package com.baeldung.configuration.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropertyBeanInjection {

    private CustomProperties customProperties;

    private JdbcProperties jdbcProperties;

    PropertyBeanInjection(@Autowired CustomProperties customProperties, @Autowired JdbcProperties jdbcProperties) {
        this.customProperties = customProperties;
        this.jdbcProperties = jdbcProperties;
    }

    String getUrl() {
        return customProperties.getUrl();
    }

    String getJdbcUrl() {
        return jdbcProperties.getJdbcUrl();
    }

    int getTimeoutInMilliseconds() {
        return customProperties.getTimeoutInMilliSeconds();
    }
}
