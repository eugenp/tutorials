package com.baeldung.dependencyinjectiontypesinspring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.baeldung.dependencyinjectiontypesinspring"})
public class InjectionConfiguration {

}
