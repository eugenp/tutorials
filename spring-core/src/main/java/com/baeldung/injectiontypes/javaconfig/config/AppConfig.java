package com.baeldung.injectiontypes.javaconfig.config;

import com.baeldung.injectiontypes.domain.Jupiter;
import com.baeldung.injectiontypes.domain.Moon;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.baeldung.injectiontypes"})
public class AppConfig {

    @Bean
    public Moon moon() {
        return new Moon(3474);
    }

    @Bean
    public Jupiter jupiter() {
        return new Jupiter(139822);
    }
}