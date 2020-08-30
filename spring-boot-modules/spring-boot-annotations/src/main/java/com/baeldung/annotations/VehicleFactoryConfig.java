package com.baeldung.annotations;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages = "com.baeldung.annotations")
@ComponentScan(basePackageClasses = VehicleFactoryConfig.class)
@ImportResource("classpath:/annotations.xml")
@PropertySource("classpath:/annotations.properties")
@Lazy
@EnableAutoConfiguration
@EnableAsync
@EnableScheduling
public class VehicleFactoryConfig {

    @Bean
    @Lazy(false)
    public Engine engine() {
        return new Engine();
    }

}
