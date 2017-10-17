package com.baeldung.constructorditwo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.constructorditwo.domain.Artist;
import com.baeldung.constructorditwo.domain.Song;

@Configuration
@ComponentScan("com.baeldung.constructorditwo")
public class Config {

    @Bean
    public Artist artist() {
        return new Artist("Yo-Yo Ma", 61);
    }

    @Bean
    public Song song() {
        return new Song("Joy to the World");
    }
}
