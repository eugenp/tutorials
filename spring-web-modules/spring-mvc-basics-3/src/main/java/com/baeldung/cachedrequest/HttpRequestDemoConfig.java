package com.baeldung.cachedrequest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * To initialize the WebApplication
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.baeldung.cachedrequest")
public class HttpRequestDemoConfig implements WebMvcConfigurer {

}