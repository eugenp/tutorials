package com.baeldung.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = { "com.baeldung.dao", "com.baeldung.aop", "com.baeldung.events" })
@EnableAspectJAutoProxy
public class TestConfig {
}
