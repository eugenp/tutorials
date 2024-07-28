package com.baeldung.spring.cloud.bootstrap.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.codec.Encoder;
import feign.codec.Decoder;
import feign.jackson.JacksonEncoder;
import feign.jackson.JacksonDecoder;

@Configuration
public class FeignConfig {

    @Bean
    public Decoder decoder(ObjectMapper objectMapper) {
        return new JacksonDecoder(objectMapper);
    }

    @Bean
    public Encoder encoder(ObjectMapper objectMapper) {
        return new JacksonEncoder(objectMapper);
    }

}
