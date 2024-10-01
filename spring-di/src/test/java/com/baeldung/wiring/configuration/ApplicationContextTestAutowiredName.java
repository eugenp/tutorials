package com.baeldung.wiring.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.baeldung.dependency"})
public class ApplicationContextTestAutowiredName {
}
