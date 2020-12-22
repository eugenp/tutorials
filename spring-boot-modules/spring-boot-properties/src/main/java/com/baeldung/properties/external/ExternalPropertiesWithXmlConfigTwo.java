package com.baeldung.properties.external;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:basicConfigForPropertiesTwo.xml")
public class ExternalPropertiesWithXmlConfigTwo {

    public ExternalPropertiesWithXmlConfigTwo() {
        super();
    }

}