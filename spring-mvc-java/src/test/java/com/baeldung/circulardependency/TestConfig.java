package com.baeldung.circulardependency;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.baeldung.circulardependency" })
public class TestConfig {

}
