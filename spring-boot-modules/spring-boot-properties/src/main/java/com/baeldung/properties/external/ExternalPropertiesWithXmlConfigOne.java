package com.baeldung.properties.external;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:configForPropertiesOne.xml")
@ComponentScan("org.baeldung.core")
public class ExternalPropertiesWithXmlConfigOne {

    public ExternalPropertiesWithXmlConfigOne() {
        super();
    }

}