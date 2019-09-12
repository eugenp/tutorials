package com.baeldung.hibernate.H2.EntityMapToTwoTables.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:h2.properties")
public class H2DBConfig {
 //No configuration required for H2DB
}
