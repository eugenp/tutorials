package com.baeldung.hexagonal.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.hexagonal.server.main.CommandLineApplication;
import com.baeldung.hexagonal.domain.repository.LibraryRepository;
import com.baeldung.hexagonal.domain.service.LibraryService;
import com.baeldung.hexagonal.domain.service.MemberLibraryService;

@Configuration
@ComponentScan(basePackageClasses = CommandLineApplication.class)
public class BeanConfiguration {

    @Bean
    LibraryService libraryService(final LibraryRepository libraryRepository) {
        return new MemberLibraryService(libraryRepository);
    }
}