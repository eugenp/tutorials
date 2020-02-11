package com.baeldung.hexagonal.infrastructure.persistence.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.baeldung.hexagonal.application.port._out.SongPersistencePort;

@Configuration
@EnableJpaRepositories
@Profile("jpa")
public class SpringDataJpaAdapterConfig {

    @Bean
    public SongPersistencePort getSongPersistencePort(SongRepository songRepository) {
        return new SongSpringJpaAdapter(songRepository);
    }
}
