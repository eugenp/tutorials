package com.baeldung.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ImportResource("classpath:webMvcConfig.xml")
@Configuration
@ComponentScan
public class ClientWebConfig implements WebMvcConfigurer {

    public ClientWebConfig() {
        super();
    }
}