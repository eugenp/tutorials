package com.baeldung.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AdditionalProperties.class)
public class AdditionalConfiguration {

    @Autowired
    private AdditionalProperties additionalProperties;

}
