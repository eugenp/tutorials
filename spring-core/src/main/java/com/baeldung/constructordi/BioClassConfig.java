package com.baeldung.constructordi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.baeldung.constructordi.domain.BioClass;

@Configuration
@ComponentScan("com.baeldung.constructordi")
public class BioClassConfig {

    @Bean
    public BioClass bioClass() {
        return new BioClass(21, 6);
    }
}