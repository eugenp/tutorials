package com.baeldung.configuration;

import com.baeldung.adapters.TrackAdapter;
import com.baeldung.ports.api.SongService;
import com.baeldung.ports.repository.SongRepository;
import com.baeldung.service.SongServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SongApplicationConfiguration {
    @Bean
    public SongRepository songRepository() {
        return new TrackAdapter();
    }

    @Bean
    public SongService songService() {
        return new SongServiceImpl(songRepository());
    }
}
