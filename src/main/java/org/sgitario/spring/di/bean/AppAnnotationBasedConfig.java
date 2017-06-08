package org.sgitario.spring.di.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.sgitario.spring.di.bean")
public class AppAnnotationBasedConfig {

    @Bean
    public DieselEngine dieselEngine() {
        return new DieselEngine();
    }

    // @Bean
    // public GasEngine gasEngine() {
    // return new GasEngine();
    // }
}
