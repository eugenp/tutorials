package org.baeldung.spring.properties;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan("org.baeldung.core")
@PropertySource("classpath:foo.properties")
public class PropertiesWithJavaConfig extends WebMvcConfigurerAdapter {

    public PropertiesWithJavaConfig() {
        super();
    }

}