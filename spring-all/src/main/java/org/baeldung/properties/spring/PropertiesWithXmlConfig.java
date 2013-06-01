package org.baeldung.properties.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:configForProperties.xml")
@ComponentScan("org.baeldung.core")
public class PropertiesWithXmlConfig {

    public PropertiesWithXmlConfig() {
        super();
    }

}