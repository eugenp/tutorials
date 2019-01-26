package org.baeldung.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
public class ClientWebConfig extends WebMvcConfigurerAdapter {

    public ClientWebConfig() {
        super();
    }

    // API

}