package com.baeldung.springboot.application;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.springframework.boot.SpringApplication.run;


@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.springboot.*")
@EnableJpaRepositories("com.baeldung.springboot.*")
@EntityScan("com.baeldung.springboot.*")
public class SpringBootMainApplication {
	
	public static void main(String[] args) {

	    start();

	}

    public static void start()  {

    	 run(SpringBootMainApplication.class);

    }
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
}
