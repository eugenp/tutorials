package com.baeldung.hexagonal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.baeldung.hexagonal.infrastructure.adapter.jpa.repository.BookJpaRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses = BookJpaRepository.class)
public class JpaConfig {
}
