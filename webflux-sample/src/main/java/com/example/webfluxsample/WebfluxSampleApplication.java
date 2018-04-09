package com.example.webfluxsample;

import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.webfluxsample.model.Student;
import com.example.webfluxsample.repository.StudentRepository;

@SpringBootApplication
public class WebfluxSampleApplication {

    @Bean
    CommandLineRunner students(StudentRepository studentRepository) {

        return args -> {
            studentRepository.deleteAll()
                .subscribe(null, null, () -> {

                    Stream.of(new Student(UUID.randomUUID()
                        .toString(), "Bao", "Viet Nam"), new Student(
                            UUID.randomUUID()
                                .toString(),
                            "Sam", "USA"),
                        new Student(UUID.randomUUID()
                            .toString(), "Ryan", "Canada"),
                        new Student(UUID.randomUUID()
                            .toString(), "Akira", "Japan"))
                        .forEach(student -> {
                            studentRepository.save(student)
                                .subscribe(System.out::println);
                        });
                });
        };

    }

    public static void main(String[] args) {
        SpringApplication.run(WebfluxSampleApplication.class, args);
    }
}
