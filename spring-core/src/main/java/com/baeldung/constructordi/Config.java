package com.baeldung.constructordi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.constructordi.domain.Engine;
import com.baeldung.constructordi.domain.Genre;
import com.baeldung.constructordi.domain.Language;
import com.baeldung.constructordi.domain.Transmission;

@Configuration
@ComponentScan("com.baeldung.constructordi")
public class Config {

    @Bean
    public Engine engine() {
        return new Engine("v8", 5);
    }

    @Bean
    public Transmission transmission() {
        return new Transmission("sliding");
    }
    
    @Bean
    public Genre genre() {
        return new Genre("comedy", "U");
    }
    
    @Bean
    public Language language() {
        return new Language("English");
    }
}
