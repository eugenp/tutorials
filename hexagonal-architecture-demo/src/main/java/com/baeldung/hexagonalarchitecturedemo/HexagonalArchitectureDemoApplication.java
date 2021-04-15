package com.baeldung.hexagonalarchitecturedemo;

import com.baeldung.hexagonalarchitecturedemo.domain.model.Quote;
import com.baeldung.hexagonalarchitecturedemo.port.outbound.IQuoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HexagonalArchitectureDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HexagonalArchitectureDemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner saveQuotes(IQuoteRepository quoteRepository) {
		return (args) -> {
			quoteRepository.saveQuote(new Quote("Stay hungry. Stay Foolish", "Steve Jobs"));
			quoteRepository.saveQuote(new Quote("Sometimes life is going to hit you in the head with a brick. Don't lose faith.", "Steve Jobs"));
		};
	}

}
