package com.baeldung.spring.cloud.archaius.additionalsources.config;

import org.apache.commons.configuration.AbstractConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.config.DynamicConfiguration;
import com.netflix.config.FixedDelayPollingScheduler;
import com.netflix.config.PolledConfigurationSource;
import com.netflix.config.sources.URLConfigurationSource;

@Configuration
public class ApplicationPropertiesConfigurations {

    @Bean
    public AbstractConfiguration addApplicationPropertiesSource() {
        PolledConfigurationSource source = new URLConfigurationSource("classpath:other-config.properties");
        return new DynamicConfiguration(source, new FixedDelayPollingScheduler());
    }

}
