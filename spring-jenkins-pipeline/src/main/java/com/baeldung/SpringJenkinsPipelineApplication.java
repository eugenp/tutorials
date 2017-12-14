package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class SpringJenkinsPipelineApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringJenkinsPipelineApplication.class, args);
	}
}
