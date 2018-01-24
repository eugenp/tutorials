package com.baeldung.injectiontypes.javaconfig.config;

import com.baeldung.injectiontypes.javaconfig.domain.constructor.Earth;
import com.baeldung.injectiontypes.javaconfig.domain.constructor.Moon;
import com.baeldung.injectiontypes.javaconfig.domain.setter.Jupiter;
import com.baeldung.injectiontypes.javaconfig.domain.setter.Uranus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.baeldung.injectiontypes.javaconfig.domain"})
public class AppConfig {

    @Bean
    public Moon moon() {
        return new Moon(3474);
    }

    @Bean
    public Earth earth() {
        return new Earth(6371);
    }

    @Bean
    public Uranus uranus() {
        return new Uranus(50724);
    }

    @Bean
    public Jupiter jupiter() {
        return new Jupiter(139822);
    }
}