package com.baeldung.hexa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.baeldung.hexa.movie.domain.MovieRepository;
import com.baeldung.hexa.movie.domain.MovieService;
import com.baeldung.hexa.movie.domain.MovieServiceImpl;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	MovieController movieController() {
		MovieRepository movieRepository = new InMemoryMovieRepository();
		MovieService service = new MovieServiceImpl(movieRepository);
		return new MovieController(service);
	}
}
