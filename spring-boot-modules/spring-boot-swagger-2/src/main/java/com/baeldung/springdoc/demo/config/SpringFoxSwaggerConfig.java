package com.baeldung.springdoc.demo.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxSwaggerConfig {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().apiInfo(metaInfo());
	}

	private ApiInfo metaInfo() {

		return new ApiInfo("Sample API REST", "API REST", "1.0", "Terms of Service", null, "Apache License Version 2.0",
				"https://www.apache.org/licesen.html", new ArrayList<>());
	}
}