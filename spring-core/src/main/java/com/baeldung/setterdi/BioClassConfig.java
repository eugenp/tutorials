package com.baeldung.setterdi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.setterdi.domain.BioClass;

@Configuration
@ComponentScan("com.baeldung.setterdi")
public class BioClassConfig {

    @Bean
    public BioClass bioClass() {
        BioClass bioClass = new BioClass();
        bioClass.setNumberOfStudent(21);
        bioClass.setNumberOfTeacher(6);
        return bioClass;
    }
}