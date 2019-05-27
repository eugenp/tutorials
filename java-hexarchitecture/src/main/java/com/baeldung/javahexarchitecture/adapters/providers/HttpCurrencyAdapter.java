package com.baeldung.javahexarchitecture.adapters.providers;

import java.math.BigDecimal;

import com.baeldung.javahexarchitecture.core.Currency;
import com.baeldung.javahexarchitecture.core.ports.CurrencyConverterPort;

/**
 * Fake currency adapter.
 * @author glopez
 *
 */
public class HttpCurrencyAdapter implements CurrencyConverterPort {

    /*
     * (non-Javadoc)
     * @see com.baeldung.javahexarchitecture.core.ports.CurrencyConverterPort#convert(
     * java.math.BigDecimal, 
     * com.baeldung.javahexarchitecture.core.Currency, 
     * com.baeldung.javahexarchitecture.core.Currency)
     */
    @Override
    public BigDecimal convert(final BigDecimal amount, final Currency from, final Currency toCurrency) {
        BigDecimal result;
        if (Currency.USD == from) {
            if (Currency.UYU == toCurrency) {
                result = amount.multiply(BigDecimal.valueOf(35.23D));
            } else {
                result = amount;
            }
        } else {
            // Currency == UYU
            if (Currency.USD == toCurrency) {
                result = amount.divide(BigDecimal.valueOf(35.23D));
            } else {
                result = amount;
            }
        }

        return result;
    }
}
