package com.baeldung.movie.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.movie.adapter.out.persistence.MovieJpaAdapter;
import com.baeldung.movie.application.MovieServiceImpl;
import com.baeldung.movie.application.port.in.MovieServicePort;
import com.baeldung.movie.application.port.out.MoviePersistencePort;

@Configuration
public class MovieConfig {

    @Bean
    public MoviePersistencePort moviePersistence(){
        return new MovieJpaAdapter();
    }

    @Bean
    public MovieServicePort movieService(){
        return new MovieServiceImpl(moviePersistence());
    }
    
    
}
