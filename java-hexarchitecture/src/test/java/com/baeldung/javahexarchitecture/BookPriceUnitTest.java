package com.baeldung.javahexarchitecture;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.javahexarchitecture.adapters.providers.HttpCurrencyConverterMock;
import com.baeldung.javahexarchitecture.core.BookPricing;
import com.baeldung.javahexarchitecture.core.Currency;
import com.baeldung.javahexarchitecture.core.ports.CurrencyConverterPort;

/**
 * Test BookPrice service.
 * @author glopez
 *
 */
public class BookPriceUnitTest {

    private BookPricing bookPrice;
    private CurrencyConverterPort currencyConverter;
    
    @BeforeEach
    public void setUp() {
      this.currencyConverter = new HttpCurrencyConverterMock();
      this.bookPrice = new BookPricing(currencyConverter);
    }
    
    @Test
    public void whenInvalidISBN_thenPriceIsNotReturned() {
        Optional<BigDecimal> maybePrice = this.bookPrice.getPrice("INVALID", Currency.USD);
        assertTrue("Price should not be returned on invalid ISBN!", !maybePrice.isPresent());
    }
 
    @Test
    public void whenValidISBN_thenPriceIsReturned() {
        // "Writing Effective Use Cases" ISBN: 9780201702255
        Optional<BigDecimal> maybePrice = this.bookPrice.getPrice("9780201702255", Currency.USD);
        assertTrue("Price should be returned on valid ISBN!", maybePrice.isPresent());
        // "Patterns of Enterprise Application Architecture" ISBN: 9780321127426
        Optional<BigDecimal> maybePrice2 = this.bookPrice.getPrice("9780321127426", Currency.USD);
        assertTrue("Price should be returned on valid ISBN!", maybePrice2.isPresent());
    }
    
}
