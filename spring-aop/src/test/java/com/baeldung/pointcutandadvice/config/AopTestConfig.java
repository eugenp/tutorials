package com.baeldung.pointcutandadvice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"com.baeldung.pointcutandadvice"})
@EnableAspectJAutoProxy
public class AopTestConfig {
}
