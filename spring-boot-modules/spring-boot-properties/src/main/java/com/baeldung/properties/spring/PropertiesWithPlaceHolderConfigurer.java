package com.baeldung.properties.spring;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesWithPlaceHolderConfigurer {

    public PropertiesWithPlaceHolderConfigurer() {
        super();
    }

    @Bean
    public PropertyPlaceholderConfigurer propertyConfigurer() {
        final PropertyPlaceholderConfigurer props = new PropertyPlaceholderConfigurer();
        props.setSystemPropertiesMode(PropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_FALLBACK);
        return props;
    }

}