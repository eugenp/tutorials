package com.baeldung.simplehexagonalex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.simplehexagonalex.controller.QuoteCliController;
import com.baeldung.simplehexagonalex.domain.QuoteOfTheDay;

@SpringBootApplication
public class DailyQuoteMain implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DailyQuoteMain.class);
    @Autowired
    private QuoteCliController quoteCliController;

    public static void main(final String[] args) {

        SpringApplication.run(DailyQuoteMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        
        QuoteOfTheDay quoteOfTheDay = quoteCliController.getQuote("cliController");
        
        LOG.info(quoteOfTheDay.toString());
    }
}
