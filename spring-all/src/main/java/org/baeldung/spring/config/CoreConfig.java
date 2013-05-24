package org.baeldung.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ImportResource("classpath:beansInXml.xml")
public class CoreConfig extends WebMvcConfigurerAdapter {

    public CoreConfig() {
        super();
    }

}