package com.springWebflux.SpringwebfluxeventStreaming;

import java.util.stream.Stream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.springWebflux.SpringwebfluxeventStreaming.model.Student;
import com.springWebflux.SpringwebfluxeventStreaming.repository.StudentRepository;


@SpringBootApplication
public class SpringWebfluxEventStreamingApplication {
	
	@Bean
	CommandLineRunner addIntoMongoDb(StudentRepository studrepo) {

		return args -> {
			studrepo.deleteAll()
			.subscribe(null, null, () -> {
				Stream.of(new Student(
						"Mathew", 1001L),new Student(
						"Eugen", 2001L),new Student(
						"Rokon", 3001L),new Student(
						"Tanmoy", 4001L))
				.forEach(std -> {
					studrepo.save(std)
					.subscribe(System.out::println);
				});
				
			});
		};						
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringWebfluxEventStreamingApplication.class, args);
	}
}
