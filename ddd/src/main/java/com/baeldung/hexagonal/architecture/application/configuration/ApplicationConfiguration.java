package com.baeldung.hexagonal.architecture.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.hexagonal.architecture.domain.service.DomainStudentService;
import com.baeldung.hexagonal.architecture.domain.service.StudentServicePort;
import com.baeldung.hexagonal.architecture.infrastructure.jpa.adapter.StudentJpaRepositoryAdpater;
import com.baeldung.hexagonal.architecture.infrastructure.jpa.repository.StudentRepository;

@Configuration
public class ApplicationConfiguration {

    private StudentJpaRepositoryAdpater studentJpaRepository(StudentRepository studentRepository) {
        return new StudentJpaRepositoryAdpater(studentRepository);
    }

    @Bean
    StudentServicePort studentService(StudentRepository studentRepository) {
        return new DomainStudentService(studentJpaRepository(studentRepository));
    }
}
