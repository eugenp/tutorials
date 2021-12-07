package com.baeldung.patterns.hexagonal.architecture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.baeldung.patterns.hexagonal.architecture.data.StockRepository;
import com.baeldung.patterns.hexagonal.architecture.domain.Stock;
import com.baeldung.patterns.hexagonal.architecture.domain.service.StockTradeService;

@SpringBootApplication
public class QuickHexagonalArchitectureCliApplication implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(QuickHexagonalArchitectureCliApplication.class);

    @Autowired
    private StockTradeService tradeService;

    public static void main(String[] args) {
        SpringApplication cli = new SpringApplication(QuickHexagonalArchitectureCliApplication.class);
        cli.setWebApplicationType(WebApplicationType.NONE);
        cli.run(args);
    }

    @Bean
    public CommandLineRunner mockStocks(StockRepository repository) {
        return (args) -> {
            log.info("mocking some stock data...");
            repository.save(new Stock("STOCKA"));
            repository.save(new Stock("STOCKB"));
        };
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.containsOption("add-stock")) {
            String ticker = args.getOptionValues("add-stock")
                .iterator()
                .next();
            tradeService.saveStock(new Stock(ticker));
        }

        if (args.containsOption("stocks")) {
            Iterable<Stock> stocks = tradeService.findAllStocks();
            for (Stock stock : stocks) {
                System.out.println("cli stock: " + stock.getTicker());
            }
        }
    }
}
