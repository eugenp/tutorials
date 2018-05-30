package com.baeldung.couchbase.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.baeldung.couchbase.spring" })
public class IntegrationTestConfig {
}
