package com.baeldung.annotations.service.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan("com.baeldung.annotations.service.abstracts")
public class AbstractsAnnotatedTestConfiguration {

}
