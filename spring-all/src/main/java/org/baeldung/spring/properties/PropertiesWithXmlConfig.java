package org.baeldung.spring.properties;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ImportResource("classpath:configForProperties.xml")
@ComponentScan("org.baeldung.core")
public class PropertiesWithXmlConfig extends WebMvcConfigurerAdapter {

    public PropertiesWithXmlConfig() {
        super();
    }

}