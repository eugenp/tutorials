package com.baeldung.hexagonalarchitecture.corejava;


import com.baeldung.hexagonalarchitecture.corejava.adapter.driving.ExchangeRatesApplicationAdapter;
import com.baeldung.hexagonalarchitecture.corejava.port.driving.ExchangeRatesApplicationPort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// represents application layer
public class ExchangeRatesApplication {

    private ExchangeRatesApplicationPort exchangeRatesApplicationPort;

    private SimpleDateFormat formatter;

    public static void main(String[] args) {
        ExchangeRatesApplication exchangeRatesApplication = new ExchangeRatesApplication();
        try {
            exchangeRatesApplication.execute(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // todo the whole app setup - can be used in diff way - just switch implementations
    public ExchangeRatesApplication() {
        exchangeRatesApplicationPort = new ExchangeRatesApplicationAdapter();
        // todo extract the format to const
        formatter = new SimpleDateFormat("dd-MMM-yyyy");
    }

    public void execute(String[] args) throws IllegalArgumentException {

        if (args.length == 0) {
            throw new IllegalArgumentException("Please provide commands.");
        }

        switch (args[0]) {
            case "currencies":
                printAllAvailableCurrencies();
                break;
            case "get":
                printExchangeRateForCurrencyAndDate(args);
                break;
            default:
                throw new IllegalArgumentException("Incorrect command provided");
        }
    }

    private void printExchangeRateForCurrencyAndDate(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Not sufficient ... ");
        }

        String dateTime = args[2];

        Date exchangeRateDate;
        try {
            exchangeRateDate = parseDate(dateTime);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date expected in different format: TODO + old");
        }

        String currencyCode = args[1];
        double exchangeRate = exchangeRatesApplicationPort.getCurrencyExchangeRatesForDate(currencyCode, exchangeRateDate);

        System.out.println(String.join(" ",
                currencyCode,
                dateTime,
                Double.toString(exchangeRate)));
    }

    private Date parseDate(String dateTime) throws ParseException {
        return formatter.parse(dateTime);
    }

    private void printAllAvailableCurrencies() {
        for (String currencyCode : exchangeRatesApplicationPort.loadAvailableCurrencies()) {
            System.out.println(currencyCode);
        }
    }
}
