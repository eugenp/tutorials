package com.baeldung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaSpringHexagonalArchitectureApplication implements CommandLineRunner {

	@Autowired
	private StudentRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(JavaSpringHexagonalArchitectureApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		StudentEntity studentEntity1 = new StudentEntity(1, "Penelope", "Arts");
		StudentEntity studentEntity2 = new StudentEntity(2, "Pippa", "Commerce");
		StudentEntity studentEntity3 = new StudentEntity(3, "Rachel", "Maths");
		StudentEntity studentEntity4 = new StudentEntity(4, "Rebecca", "Economics");
		StudentEntity studentEntity5 = new StudentEntity(5, "Rose", "Geology");

		repository.save(studentEntity1);
		repository.save(studentEntity2);
		repository.save(studentEntity3);
		repository.save(studentEntity4);
		repository.save(studentEntity5);
	}
}