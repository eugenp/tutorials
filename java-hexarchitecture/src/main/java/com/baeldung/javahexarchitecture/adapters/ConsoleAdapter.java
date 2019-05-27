package com.baeldung.javahexarchitecture.adapters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.javahexarchitecture.core.Currency;
import com.baeldung.javahexarchitecture.core.ports.BookPricingPort;

/**
 * One of many other adapter implementations.
 * @author glopez
 *
 */
public class ConsoleAdapter {

    /**
     * Application logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleAdapter.class);
    
    /**
     * Character required to terminate the execution.
     */
    private static final String CONTINUE = "y";

    /**
     * Service required by the console adapter.
     */
    private transient final BookPricingPort bookPricePort;

    /**
     * 
     * @param bpp
     */
    public ConsoleAdapter(final BookPricingPort bpp) {
        this.bookPricePort = bpp;
    }

    /**
     * Application entry point.
     */
    public void run() {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = "y";
        try {
            while (CONTINUE.equalsIgnoreCase(line)) {
                LOGGER.info("Type ISBN to search for:");
                final String isbn = reader.readLine();
                LOGGER.info("Type Currency (1 = UYU, 2 = USD):");
                final String currency = reader.readLine();
                final Optional<BigDecimal> maybePrice = this.bookPricePort.getPrice(isbn, "1".equals(currency) ? Currency.UYU : Currency.USD);
                LOGGER.info(
                    maybePrice.isPresent() ? String.valueOf(maybePrice.get()) : 
                        "Not available...");
                LOGGER.info("Search another one (y/n?");
                line = reader.readLine();
            }
            LOGGER.info("Bye...");
        } catch (IOException e) {
            LOGGER.error("Unexpected error occurred: ", e);
            throw new RuntimeException(e);
        }
    }
}
