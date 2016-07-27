package com.baeldung.mvc.velocity.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.baeldung.mvc.velocity.service" })
public class SpringConfig {

}
