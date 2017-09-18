package com.baeldung.setterditwo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.setterditwo.domain.Artist;
import com.baeldung.setterditwo.domain.Song;

@Configuration
@ComponentScan("com.baeldung.setterditwo")
public class Config {

    @Bean
    public Artist artist() {
        Artist artist = new Artist();
        artist.setName("Yo-Yo Ma");
        artist.setAge(61);
        return artist;
    }

    @Bean
    public Song song() {
        Song song = new Song();
        song.setName("Joy to the World");
        return song;
    }
}