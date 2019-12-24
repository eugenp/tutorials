package com.baeldung;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = { "com.baeldung.pointcutadvice" })
@EnableAspectJAutoProxy
public class TestConfig {
}
