package com.baeldung.hexagonal.architecture.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.hexagonal.architecture.domain.services.DomainStudentService;
import com.baeldung.hexagonal.architecture.domain.services.StudentServicePort;
import com.baeldung.hexagonal.architecture.infrastructure.jpa.adapters.StudentJpaRepositoryAdpater;
import com.baeldung.hexagonal.architecture.infrastructure.jpa.repositories.StudentRepository;

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
