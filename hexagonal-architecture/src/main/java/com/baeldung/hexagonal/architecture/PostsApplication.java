package com.baeldung.hexagonal.architecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Configuration
public class PostsApplication {
	public static void main(String[] args) {
		SpringApplication.run(PostsApplication.class, args);
	}
}
