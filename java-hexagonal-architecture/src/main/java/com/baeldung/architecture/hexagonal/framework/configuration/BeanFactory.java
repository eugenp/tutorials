package com.baeldung.architecture.hexagonal.framework.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.baeldung.architecture.hexagonal.domain.repository.StudentRepository;
import com.baeldung.architecture.hexagonal.domain.service.StudentService;
import com.baeldung.architecture.hexagonal.domain.service.StudentServiceImpl;

@Configuration
public class BeanFactory {

    @Bean
    @Primary
    StudentService studentAdminServiceForH2(
      @Qualifier("h2Adapter") StudentRepository studentRepository) {
        return new StudentServiceImpl(studentRepository);
    }

    @Bean
    StudentService studentAdminServiceForFile(
      @Qualifier("fileAdapter") StudentRepository studentRepository) {
        return new StudentServiceImpl(studentRepository);
    }
}