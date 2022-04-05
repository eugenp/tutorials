package com.baeldung.properties.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:configForPropertiesTwo.xml")
public class PropertiesWithXmlConfigTwo {

    public PropertiesWithXmlConfigTwo() {
        super();
    }

}