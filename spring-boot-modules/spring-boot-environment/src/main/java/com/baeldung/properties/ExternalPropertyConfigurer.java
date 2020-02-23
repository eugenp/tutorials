package com.baeldung.properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ExternalPropertyConfigurer {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
        properties.setLocation(new FileSystemResource("src/main/resources/external/conf.properties"));
        properties.setIgnoreResourceNotFound(false);
        return properties;
    }
}