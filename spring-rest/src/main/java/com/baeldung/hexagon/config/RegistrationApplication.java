package com.baeldung.hexagon.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.SpringServletContainerInitializer;

@Configuration
@ComponentScan({ "com.baeldung.hexagon" })
@SpringBootApplication
@EnableJpaRepositories("com.baeldung.hexagon.dao")
@EntityScan("com.baeldung.hexagon.dao")
public class RegistrationApplication extends SpringServletContainerInitializer {

	public static void main(final String[] args) {
		SpringApplication.run(RegistrationApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}