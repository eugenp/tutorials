package com.baeldung.sampleapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.baeldung.sampleapp.web" })
public class WebConfig implements WebMvcConfigurer {

    public WebConfig() {
        super();
    }

}
