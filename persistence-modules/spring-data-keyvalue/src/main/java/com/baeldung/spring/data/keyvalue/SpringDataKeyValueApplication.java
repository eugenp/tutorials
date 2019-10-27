package com.baeldung.spring.data.keyvalue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.map.repository.config.EnableMapRepositories;

@SpringBootApplication
@EnableMapRepositories
public class SpringDataKeyValueApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringDataKeyValueApplication.class, args);
	}

}
