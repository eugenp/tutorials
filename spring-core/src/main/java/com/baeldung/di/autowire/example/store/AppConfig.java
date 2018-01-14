package com.baeldung.di.autowire.example.store;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.baeldung.di.autowire.example.store" })
public class AppConfig {

}
