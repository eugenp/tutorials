package org.baeldung.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("org.baeldung.core")
public class CoreConfig extends WebMvcConfigurerAdapter {

    public CoreConfig() {
        super();
    }

}