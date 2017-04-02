package com.baeldung.dependencyinjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.dependencyinjection.model.Keyboard;
import com.baeldung.dependencyinjection.model.Mouse;

@Configuration
@ComponentScan("com.baeldung.dependencyinjection")
public class ConstructorInjectConfig {

    @Bean
    public Keyboard keyboard() {
        return new Keyboard("DVORAK");
    }

    @Bean
    public Mouse mouse() {
        return new Mouse("Wired");
    }

}
