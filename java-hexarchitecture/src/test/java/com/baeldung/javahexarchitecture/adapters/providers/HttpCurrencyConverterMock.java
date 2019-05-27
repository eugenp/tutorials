package com.baeldung.javahexarchitecture.adapters.providers;

import java.math.BigDecimal;

import com.baeldung.javahexarchitecture.core.Currency;
import com.baeldung.javahexarchitecture.core.ports.CurrencyConverterPort;

/**
 * Mocked HttpCurrencyConverter.
 * @author glopez
 *
 */
public class HttpCurrencyConverterMock implements CurrencyConverterPort {

    /*
     * (non-Javadoc)
     * @see com.baeldung.javahexarchitecture.core.ports.CurrencyConverterPort#convert(
     * java.math.BigDecimal, 
     * com.baeldung.javahexarchitecture.core.Currency, 
     * com.baeldung.javahexarchitecture.core.Currency)
     */
    @Override
    public BigDecimal convert(BigDecimal amount, Currency from, Currency toCurrency) {
        return BigDecimal.valueOf(123.43D);
    }

}
