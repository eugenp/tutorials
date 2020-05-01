package com.baeldung.properties.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:configForProperties.xml")
@ComponentScan("com.baeldung.core")
public class PropertiesWithXmlConfig {

    public PropertiesWithXmlConfig() {
        super();
    }

}