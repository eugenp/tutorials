package org.baeldung.properties.parentchild.config;

import org.baeldung.properties.parentchild.ChildValueHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:child.properties")
public class ChildConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ChildValueHolder childValueHolder() {
        return new ChildValueHolder();
    }
}
