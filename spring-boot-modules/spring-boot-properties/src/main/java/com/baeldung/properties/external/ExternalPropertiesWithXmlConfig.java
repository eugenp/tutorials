package com.baeldung.properties.external;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:configForProperties.xml")
@ComponentScan("com.baeldung.core")
public class ExternalPropertiesWithXmlConfig {

    public ExternalPropertiesWithXmlConfig() {
        super();
    }

}