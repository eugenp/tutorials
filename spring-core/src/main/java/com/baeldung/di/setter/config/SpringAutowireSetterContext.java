package com.baeldung.di.setter.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.baeldung.di.setter.model.autowire" })
public class SpringAutowireSetterContext {

}
