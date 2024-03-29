package com.baeldung.globaltimezone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

    @Bean
    public GlobalTimezoneBeanFactoryPostProcessor gimezoneBeanFactoryPostProcessor() {
        return new GlobalTimezoneBeanFactoryPostProcessor();
    }

    @Bean
    public GlobalTimeZoneBean globalTimeZoneBean() {
        return new GlobalTimeZoneBean();
    }
}
