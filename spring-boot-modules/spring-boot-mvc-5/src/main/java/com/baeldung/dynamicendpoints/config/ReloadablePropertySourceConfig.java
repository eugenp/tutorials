package com.baeldung.dynamicendpoints.config;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

@Configuration
public class ReloadablePropertySourceConfig {

    private ConfigurableEnvironment env;

    public ReloadablePropertySourceConfig(@Autowired ConfigurableEnvironment env) {
        this.env = env;
    }

    @Bean
    @ConditionalOnProperty(name = "dynamic.endpoint.config.location", matchIfMissing = false)
    public ReloadablePropertySource reloadablePropertySource(PropertiesConfiguration properties) {
        ReloadablePropertySource reloadablePropertySource = new ReloadablePropertySource("toggle-endpoints", properties);
        MutablePropertySources sources = env.getPropertySources();
        sources.addFirst(reloadablePropertySource);
        return reloadablePropertySource;
    }
}