package com.baeldung.overridebean.profile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.baeldung.overridebean.Service;
import com.baeldung.overridebean.ServiceImpl;

@Configuration
@Profile("prod")
public class ProfileConfig {

    @Bean
    public Service helloWorld() {
        return new ServiceImpl();
    }
}
