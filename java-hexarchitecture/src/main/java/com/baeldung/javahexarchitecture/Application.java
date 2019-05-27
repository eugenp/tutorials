package com.baeldung.javahexarchitecture;

import com.baeldung.javahexarchitecture.adapters.ConsoleAdapter;
import com.baeldung.javahexarchitecture.adapters.providers.HttpCurrencyAdapter;
import com.baeldung.javahexarchitecture.core.BookPricing;
import com.baeldung.javahexarchitecture.core.ports.BookPricingPort;
import com.baeldung.javahexarchitecture.core.ports.CurrencyConverterPort;

/**
 * The startup component is the one doing the component wiring. 
 * Should we be using a DI framework like Spring, all the wiring 
 * would have been delegated to it.
 * @author glopez
 *
 */
public class Application {

    /**
     * 
     */
    private Application() {
    }

    /**
     * 
     * @param args
     */
    public static void main(final String[] args) {
        final CurrencyConverterPort currencyConverter = new HttpCurrencyAdapter();
        final BookPricingPort bpp = new BookPricing(currencyConverter);
        final ConsoleAdapter consoleAdapter = new ConsoleAdapter(bpp);
        consoleAdapter.run();
    }

}
