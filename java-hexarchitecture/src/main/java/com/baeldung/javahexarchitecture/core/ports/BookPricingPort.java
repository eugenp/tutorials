package com.baeldung.javahexarchitecture.core.ports;

import java.math.BigDecimal;
import java.util.Optional;

import com.baeldung.javahexarchitecture.core.Currency;

/**
 * Application/domain interface providing price for books by ISBN.
 * @author glopez
 *
 */
public interface BookPricingPort {

    /**
     * Provides the price of the book identified by the given isbn 
     * and with the given currency.
     * @param isbn book identifier
     * @param currency desired currency
     * @return the books price in the given currency.
     */
    Optional<BigDecimal> getPrice(String isbn, Currency currency);
}
