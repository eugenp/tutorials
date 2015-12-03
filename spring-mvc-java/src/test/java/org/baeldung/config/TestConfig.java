package org.baeldung.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"org.baeldung.dao", "org.baeldung.aop", "org.baeldung.events"})
@EnableAspectJAutoProxy
public class TestConfig {
}
