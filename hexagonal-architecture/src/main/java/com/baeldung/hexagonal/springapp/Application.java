package com.baeldung.hexagonal.springapp;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.baeldung.hexagonal.core.TodoRepository;
import com.baeldung.hexagonal.core.TodoService;
import com.baeldung.hexagonal.springapp.repository.JpaTodoRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = JpaTodoRepository.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public TodoService todoService(TodoRepository todoRepository) {
		return new TodoService(LocalDate::now, todoRepository);
	}
}
