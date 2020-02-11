package com.baeldung.hexagonal.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.hexagonal.application.port.in.SongService;
import com.baeldung.hexagonal.application.port.in.SongUseCase;
import com.baeldung.hexagonal.application.port.out.SongPersistencePort;

@Configuration
public class Config {

    @Bean
    public SongUseCase getSongService(SongPersistencePort songPersistencePort) {
        return new SongService(songPersistencePort);
    }
}
