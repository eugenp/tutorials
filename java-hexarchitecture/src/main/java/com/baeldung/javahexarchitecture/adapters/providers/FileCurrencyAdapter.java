package com.baeldung.javahexarchitecture.adapters.providers;

import java.math.BigDecimal;

import org.apache.commons.lang3.NotImplementedException;

import com.baeldung.javahexarchitecture.core.Currency;
import com.baeldung.javahexarchitecture.core.ports.CurrencyConverterPort;

/**
 * Fake currency adapter getting data from file.
 * @author glopez
 *
 */
public class FileCurrencyAdapter implements CurrencyConverterPort {


    /*
     * (non-Javadoc)
     * @see com.baeldung.javahexarchitecture.core.ports.CurrencyConverterPort#convert(
     * java.math.BigDecimal, 
     * com.baeldung.javahexarchitecture.core.Currency, 
     * com.baeldung.javahexarchitecture.core.Currency)
     */
    @Override
    public BigDecimal convert(final BigDecimal amount, final Currency from, final Currency toCurrency) {
        // Logic to get conversion from http service provider.
        throw new NotImplementedException("Conversion from FileCurrencyAdapter has not been implemented yet!");
    }

}
