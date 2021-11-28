package com.baeldung.hexagonalarch.config;

import com.baeldung.hexagonalarch.repository.StudentRepository;
import com.baeldung.hexagonalarch.service.StudentService;
import com.baeldung.hexagonalarch.service.impl.StudentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfig {

    @Bean
    @Primary
    public static StudentService getStudentService(StudentRepository studentRepository) {
      return new StudentServiceImpl(studentRepository);
    }

}
