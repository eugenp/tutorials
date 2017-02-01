package com.baeldung.autowire.sample;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.baeldung.autowire.sample","com.baeldung.injection"})
public class AppConfig {

}
