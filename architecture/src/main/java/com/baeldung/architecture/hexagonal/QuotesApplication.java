package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.controllers.ConsoleQuoteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = { "classpath:hexagonal-architecture.properties" })
public class QuotesApplication implements CommandLineRunner {

    @Autowired
    private ConsoleQuoteController consoleQuoteController;

    public static void main(final String[] args) {
        SpringApplication application = new SpringApplication(QuotesApplication.class);
        // uncomment to run just the console application
        // application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Override
    public void run(String... args) {
        this.consoleQuoteController.getRandomQuote();
        this.consoleQuoteController.getAllQuotes();
    }

}
