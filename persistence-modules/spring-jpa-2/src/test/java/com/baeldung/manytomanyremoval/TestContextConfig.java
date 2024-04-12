package com.baeldung.manytomanyremoval;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/manytomanyremoval/test.properties")
@ComponentScan("com.baeldung.manytomanyremoval")
public class TestContextConfig {

}
