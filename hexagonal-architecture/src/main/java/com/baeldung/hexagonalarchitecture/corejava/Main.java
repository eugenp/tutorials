package com.baeldung.hexagonalarchitecture.corejava;

public class Main {
    public static void main(String[] args) {
        ExchangeRatesApplication exchangeRatesApplication = new ExchangeRatesApplication();
        try {
            System.out.println(exchangeRatesApplication.execute(args));
        } catch (Exception e) {
            System.out.println(String.format("ERROR: %s", e.getMessage()));
        }
    }
}
