package com.baeldung.di.constructorbased;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = { "com.baeldung.di.domain", "com.baeldung.di.constructorbased" })
public class Config {

}
