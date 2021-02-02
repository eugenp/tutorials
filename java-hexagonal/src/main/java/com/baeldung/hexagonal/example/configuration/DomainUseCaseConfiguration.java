package com.baeldung.hexagonal.example.configuration;

import com.baeldung.hexagonal.example.domain.service.ReviewMovieService;
import com.baeldung.hexagonal.example.port.output.ReviewStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainUseCaseConfiguration {
    @Bean
    public ReviewMovieService reviewMovieService(ReviewStore reviewStore) {
        return new ReviewMovieService(reviewStore);
    }
}
