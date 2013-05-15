package org.baeldung.spring.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan({ "org.baeldung.spring.web.controller" })
public class MvcConfig {

    public MvcConfig() {
        super();
    }

}