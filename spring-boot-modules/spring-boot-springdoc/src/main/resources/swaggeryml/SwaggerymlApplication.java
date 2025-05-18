package com.baeldung.swaggeryml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SwaggerymlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwaggerymlApplication.class, args);
//		new SpringApplicationBuilder(SwaggerymlApplication.class)
//			.properties("spring.config.name=application-yml")
//			.run(args);
	}

}
