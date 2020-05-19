package com.baeldung.spring.cloud.archaius.jdbconfig.config;

import javax.sql.DataSource;

import org.apache.commons.configuration.AbstractConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.config.DynamicConfiguration;
import com.netflix.config.FixedDelayPollingScheduler;
import com.netflix.config.PolledConfigurationSource;
import com.netflix.config.sources.JDBCConfigurationSource;

@Configuration
public class ApplicationPropertiesConfigurations {

    @Autowired
    DataSource h2DataSource;

    @Bean
    public AbstractConfiguration addApplicationPropertiesSource() {
        PolledConfigurationSource source = new JDBCConfigurationSource(h2DataSource, "select distinct key, value from properties", "key", "value");
        return new DynamicConfiguration(source, new FixedDelayPollingScheduler());
    }

}
