package org.baeldung.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("org.baeldung.test")
public class ConfigTest extends WebMvcConfigurerAdapter {

    public ConfigTest() {
        super();
    }

    // API

}