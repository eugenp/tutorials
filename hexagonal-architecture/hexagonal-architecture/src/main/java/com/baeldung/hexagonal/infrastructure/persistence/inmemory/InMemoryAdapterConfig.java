package com.baeldung.hexagonal.infrastructure.persistence.inmemory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.baeldung.hexagonal.application.port._out.SongPersistencePort;

@Configuration
//@Profile("in-memory")
public class InMemoryAdapterConfig {

	@Bean
    public SongPersistencePort getSongPersistencePort() {
        return new SongInMemoryAdapter();
    }
	
}
