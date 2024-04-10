package com.baeldung.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import jakarta.servlet.ServletContext;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"com.baeldung.controller.parameterized"})
public class WebConfig {

    @Autowired
    private ServletContext ctx;
}
