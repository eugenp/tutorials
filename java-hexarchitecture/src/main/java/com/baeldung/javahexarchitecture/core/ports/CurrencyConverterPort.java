package com.baeldung.javahexarchitecture.core.ports;

import java.math.BigDecimal;

import com.baeldung.javahexarchitecture.core.Currency;

/**
 * Interface for currency converter providers.
 * @author glopez
 *
 */
public interface CurrencyConverterPort {

    /**
     * Provides current value
     * @param amount any given amount of money
     * @param from origin currency
     * @param toCurrency destination currency
     * @return
     */
    BigDecimal convert(BigDecimal amount, Currency from, Currency toCurrency);

}
