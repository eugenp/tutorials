package com.baeldung.properties.parentchild.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.baeldung.properties.parentchild.ParentValueHolder;

@Configuration
@PropertySource("classpath:parent.properties")
public class ParentConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ParentValueHolder parentValueHolder() {
        return new ParentValueHolder();
    }

}
