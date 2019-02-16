package com.baeldung.springbootconfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import org.springframework.context.annotation.Import;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration;


@SpringBootConfiguration
@Import({
    WebFluxAutoConfiguration.class,
    ReactiveWebServerFactoryAutoConfiguration.class,
    ErrorWebFluxAutoConfiguration.class,
    HttpHandlerAutoConfiguration.class,
    ConfigurationPropertiesAutoConfiguration.class,
    PropertyPlaceholderAutoConfiguration.class
})
@RestController
public class SpringBootConfigurationApplication {

	@GetMapping("/")
  	public Mono<String> home() {
    	return Mono.just("Hello World");
  	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootConfigurationApplication.class, args);
	}

}
