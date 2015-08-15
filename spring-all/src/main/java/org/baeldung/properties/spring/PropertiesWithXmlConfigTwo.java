package org.baeldung.properties.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:basicConfigForPropertiesTwo.xml")
public class PropertiesWithXmlConfigTwo {

    public PropertiesWithXmlConfigTwo() {
        super();
    }

}