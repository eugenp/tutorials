package com.baeldung.rest;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.baeldung.rest")
@EnableAutoConfiguration
@PropertySource("classpath:rest-app.properties")
public class RestConfig {

}
