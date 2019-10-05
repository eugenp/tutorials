package com.baeldung.hexagonal.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.baeldung.hexagonal.port.outbound.BookOutboundPort;

@Profile("test-book-service")
@Configuration
public class BookServiceTestConfiguration {

    @Bean
    @Primary
    public BookOutboundPort nameServiceTest() {
        return Mockito.mock(BookOutboundPort.class);
    }
}