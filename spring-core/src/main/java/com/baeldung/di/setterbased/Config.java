package com.baeldung.di.setterbased;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = { "com.baeldung.di.domain", "com.baeldung.di.setterbased" })
public class Config {

}
