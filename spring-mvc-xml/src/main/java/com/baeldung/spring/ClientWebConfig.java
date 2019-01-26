package com.baeldung.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ImportResource("classpath:webMvcConfig.xml")
@Configuration
public class ClientWebConfig extends WebMvcConfigurerAdapter {

    public ClientWebConfig() {
        super();
    }

    // API

}