package com.baeldung.rate.app;

import com.baeldung.rate.ExchangeRate;

import java.time.LocalDate;

public class MainApp {
    public static void main(String... args) {
        ExchangeRate.providers().forEach(provider -> {
            System.out.println("Retreiving quotes from provider :" + provider);
            provider.create().getQuotes("USD", LocalDate.now()).forEach(quote -> {
                System.out.println("USD --" + quote.getCurrency() + ": " + quote.getAsk() + "+++" + quote.getBid());
            });
        });
    }
}
