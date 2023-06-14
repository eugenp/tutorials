package com.baeldung.spring.actuator;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackageClasses = AppConfig.class)
public class AppConfig {
}
