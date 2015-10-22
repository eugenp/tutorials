package org.baeldung.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"org.baeldung.dao", "org.baeldung.aop"})
@EnableAspectJAutoProxy
public class TestConfig {
}
