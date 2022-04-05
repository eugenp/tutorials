package com.baeldung.annotations.componentscanautoconfigure.healthcare;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Hospital {

    @Bean("doctor")
    public Doctor getDoctor() {
        return new Doctor();
    }
}
