package com.baeldung.di.types.auto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.baeldung.di.types.Formatter;
import com.baeldung.di.types.JavaFormatter;

@Profile("autowired-inject")
@ComponentScan("com.baeldung.di.types.auto")
@Configuration
public class AutowiredFormatterConfiguration {

    @Bean
    public Formatter javaFormatter() {
        return new JavaFormatter();
    }

}
