package com.baeldung.hexagon;

import com.baeldung.hexagon.repository.StudentRepository;
import com.baeldung.hexagon.service.StudentServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class JavaHexagonalArchitectureApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaHexagonalArchitectureApplication.class, args);
    }

    @Bean
    public StudentServiceImpl getStudentService(StudentRepository repository) {
        return new StudentServiceImpl(repository);
    }
}
