package com.baeldung.authoronboardsathish;

import com.baeldung.authoronboardsathish.domain.PianoMusicMaker;
import com.baeldung.authoronboardsathish.domain.ViolinMusicMaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.setterdi")
public class Config {

    @Bean
    public PianoMusicMaker pianoMusicMaker() {
        PianoMusicMaker pianoMusicMaker = new PianoMusicMaker();
        pianoMusicMaker.setNameOfMusic("Sonatina G");
        return pianoMusicMaker;
    }

    @Bean
    public ViolinMusicMaker violinMusicMaker() {
        ViolinMusicMaker violinMusicMaker = new ViolinMusicMaker();
        violinMusicMaker.setNameOfMusic("Concerto E minor OP.64");
        violinMusicMaker.setNoteType("Forte");
        return violinMusicMaker;
    }
}