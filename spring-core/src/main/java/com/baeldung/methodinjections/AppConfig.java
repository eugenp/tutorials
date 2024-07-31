package com.baeldung.methodinjections;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.baeldung.methodinjections")

public class AppConfig {
}
