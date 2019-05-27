package com.baeldung.javahexarchitecture.core;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.baeldung.javahexarchitecture.core.ports.BookPricingPort;
import com.baeldung.javahexarchitecture.core.ports.CurrencyConverterPort;

/**
 * Domain logic.
 * @author glopez
 *
 */
public class BookPricing implements BookPricingPort {

    /**
     * Prices (USD) by book ISBN.
     */
    private transient final Map<String, BigDecimal> pricesByBookISBN = new ConcurrentHashMap<>();

    /**
     * Currency converter service.
     */
    private CurrencyConverterPort currencyConverter;

    /**
     * 
     */
    public BookPricing() {
        // "Writing Effective Use Cases" ISBN: 9780201702255
        this.pricesByBookISBN.put("9780201702255", BigDecimal.valueOf(123.43D));
        // "Patterns of Enterprise Application Architecture" ISBN: 9780321127426
        this.pricesByBookISBN.put("9780321127426", BigDecimal.valueOf(100.67D));
    }

    /**
     * 
     * @param ccp
     */
    public BookPricing(final CurrencyConverterPort ccp) {
        this();
        this.currencyConverter = ccp;
    }

    public CurrencyConverterPort getCurrencyConverter() {
        return currencyConverter;
    }

    public void setCurrencyConverter(final CurrencyConverterPort currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    /*
     * (non-Javadoc)
     * @see com.baeldung.javahexarchitecture.core.ports
     * .BookPricePort#getPrice(java.lang.String, 
     * com.baeldung.javahexarchitecture.core.Currency)
     */
    @Override
    public Optional<BigDecimal> getPrice(final String isbn, final Currency currency) {
        BigDecimal price = this.pricesByBookISBN.get(isbn);
        Optional<BigDecimal> maybePrice;
        if (Currency.USD == currency) {
            maybePrice = Optional.ofNullable(price);
        } else {
            if (price == null) {
                maybePrice = Optional.ofNullable(price);
            } else {
                price = this.getCurrencyConverter().convert(price, Currency.USD, Currency.UYU);
                maybePrice = Optional.ofNullable(price);
            }
        }
        return maybePrice;
    }
}
