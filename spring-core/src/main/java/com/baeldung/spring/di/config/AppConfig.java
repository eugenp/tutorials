package com.baeldung.spring.di.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.baeldung.spring.di.*"})
public class AppConfig {
   
}
