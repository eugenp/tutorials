package com.baeldung.context2;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.baeldung.contexts.context2")
@EnableAutoConfiguration
@PropertySource("classpath:ctx2.properties")
public class Context2Config {

}