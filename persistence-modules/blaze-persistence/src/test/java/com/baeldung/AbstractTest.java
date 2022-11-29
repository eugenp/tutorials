package com.baeldung;

import com.blazebit.persistence.integration.view.spring.EnableEntityViews;
import com.blazebit.persistence.spring.data.repository.config.EnableBlazeRepositories;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

abstract class AbstractTest {

    @Configuration
    @ComponentScan("com.baeldung")
    @EnableEntityViews(basePackages = { "com.baeldung.view"})
    @EnableBlazeRepositories(
            basePackages = "com.baeldung.repository")
    static class TestConfig {
    }
}
