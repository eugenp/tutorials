package com.baeldung.hexagonalarchitecture.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.baeldung.hexagonalarchitecture.business.repository.ConcertRepository;
import com.baeldung.hexagonalarchitecture.business.service.ConcertService;
import com.baeldung.hexagonalarchitecture.business.service.ConcertServiceImpl;
import com.baeldung.hexagonalarchitecture.infrastructure.repository.InMemoryConcertRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class Config {

    @Bean
    public ConcertRepository concertRepository() {
        return new InMemoryConcertRepository();
    }

    @Bean
    public ConcertService concertService(final ConcertRepository concertRepository) {
        return new ConcertServiceImpl(concertRepository);
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(final ObjectMapper objectMapper) {
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

}
