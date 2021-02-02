package com.baeldung.hexagonal.example.configuration;

import com.baeldung.hexagonal.example.adapter.ReviewControllerHttpAdapter;
import com.baeldung.hexagonal.example.domain.service.ReviewMovieService;
import com.baeldung.hexagonal.example.port.input.ReviewController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InputAdapterConfiguration {
    @Bean
    public ReviewController inputPortController(ReviewMovieService reviewMovieService) {
        return new ReviewControllerHttpAdapter(reviewMovieService);
    }
}
