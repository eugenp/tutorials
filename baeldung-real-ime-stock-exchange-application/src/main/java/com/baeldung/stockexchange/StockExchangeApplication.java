package com.baeldung.stockexchange;

import com.baeldung.stockexchange.models.Stock;
import com.baeldung.stockexchange.repository.StockRepository;
import java.util.stream.Stream;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockExchangeApplication
{
	@Bean
	CommandLineRunner insertStocks(StockRepository stockRepository)
	{
		return args -> {
			stockRepository.deleteAll().subscribe(null, null, () -> {
				Stream.of(
						new Stock("0", "Apple (AAPL)", 78.0F),
						new Stock("1", "Google (GOOG)", 63.10F),
						new Stock("2", "General Motors (GM)", 23.57F),
						new Stock("3", "Canadian Solar (CSIQ)", 132.18F),
						new Stock("4", "Visa (V)", 53.41F)
				).forEach(stock -> {
					stockRepository.save(stock).subscribe();
				});
			});
		};
	}

	public static void main(String[] args)
	{
		SpringApplication.run(StockExchangeApplication.class, args);

		StockExchangeWebClient stockExchangeWebClient = new StockExchangeWebClient();
		stockExchangeWebClient.getStocks();
	}
}
