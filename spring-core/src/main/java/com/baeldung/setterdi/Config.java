package com.baeldung.setterdi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.setterdi.domain.Engine;
import com.baeldung.setterdi.domain.Genre;
import com.baeldung.setterdi.domain.Language;
import com.baeldung.setterdi.domain.Trailer;
import com.baeldung.setterdi.domain.Transmission;

@Configuration
@ComponentScan("com.baeldung.setterdi")
public class Config {

    @Bean
    public Engine engine() {
        Engine engine = new Engine();
        engine.setType("v8");
        engine.setVolume(5);
        return engine;
    }

    @Bean
    public Transmission transmission() {
        Transmission transmission = new Transmission();
        transmission.setType("sliding");
        return transmission;
    }

    @Bean
    public Trailer trailer() {
        Trailer trailer = new Trailer();
        return trailer;
    }
    
    @Bean
    public Genre genre() {
        Genre genre = new Genre();
        genre.setCategory("comedy");
        genre.setRating("U");
        return genre;
    }
    
    @Bean
    public Language language() {
        Language language = new Language();
        language.setValue("English");
        return language;
    }
}