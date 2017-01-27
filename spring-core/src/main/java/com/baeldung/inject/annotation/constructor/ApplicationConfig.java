package com.baeldung.inject.annotation.constructor;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Oleg Cherednik
 * @since 27.01.2017
 */
@Configurable
@ComponentScan(basePackageClasses = SomeService.class)
public class ApplicationConfig {
    @Bean("a")
    public AnotherService createAnotherServiceA() {
        return new AnotherService();
    }

    @Bean("b")
    public AnotherService createAnotherServiceB() {
        return new AnotherService();
    }
}
