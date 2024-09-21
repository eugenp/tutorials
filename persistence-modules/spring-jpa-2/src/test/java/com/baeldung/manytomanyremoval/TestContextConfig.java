package com.baeldung.manytomanyremoval;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/inhertiancesubtypes/test.properties")
@ComponentScan({"com.baeldung.jpa.subtypes.entity","com.baeldung.jpa.subtypes.repository"})
public class TestContextConfig {

}
