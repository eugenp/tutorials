package com.baeldung.simplehexagonal.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.baeldung.simplehexagonal.ConferenceApplication;
import com.baeldung.simplehexagonal.domain.repository.SessionRepository;
import com.baeldung.simplehexagonal.domain.repository.SpeakerRepository;
import com.baeldung.simplehexagonal.domain.services.SessionService;
import com.baeldung.simplehexagonal.domain.services.SessionServiceImpl;
import com.baeldung.simplehexagonal.domain.services.SpeakerService;
import com.baeldung.simplehexagonal.domain.services.SpeakerServiceImpl;

@Configuration
@ComponentScan(basePackageClasses = ConferenceApplication.class)
@EnableJpaRepositories(basePackages = "com.baeldung.simplehexagonal")
public class BeanConfiguration {

    @Bean
    SessionService sessionService(SessionRepository sessionRepository) {
        return new SessionServiceImpl(sessionRepository);
    }

    @Bean
    SpeakerService speakerService(SpeakerRepository speakerRepository) {
        return new SpeakerServiceImpl(speakerRepository);
    }
}
