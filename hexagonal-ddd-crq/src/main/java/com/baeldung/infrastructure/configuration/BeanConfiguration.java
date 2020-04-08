package com.baeldung.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.ApplicationRunner;
import com.baeldung.domain.repository.ChgRequestRepository;
import com.baeldung.domain.service.ChgRequestService;
import com.baeldung.domain.service.ChgRequestServiceImpl;

@Configuration
@ComponentScan(basePackageClasses = ApplicationRunner.class)
public class BeanConfiguration {

    @Bean
    ChgRequestService orderService(final ChgRequestRepository chgRequestRepository) {
        return new ChgRequestServiceImpl(chgRequestRepository);
    }
}
