package com.baeldung.nopropertyfoundfortype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NoPropertyFoundForTypeApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(NoPropertyFoundForTypeApplication.class, args);
	}

	@Override
	public void run(String... args) {

		repository.save(new Person("Tony", "Stark"));
		repository.save(new Person("Spider", "Man"));
		repository.save(new Person("Sachin", "Kumar"));

		repository.findByFirstName("Sachin").forEach(x -> System.out.println(x));
		repository.findByLastName("Man").forEach(x -> System.out.println(x));

	}

}
