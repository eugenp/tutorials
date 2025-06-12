package com.baeldung.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.mapper.MediaMapper;
import com.baeldung.service.MediaService;

@Configuration
public class Config {

    @Bean
    public MediaService mediaService(MediaMapper mediaMapper) {
        return new MediaService(mediaMapper);
    }

}
