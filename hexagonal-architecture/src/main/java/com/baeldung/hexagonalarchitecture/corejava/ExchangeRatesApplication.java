package com.baeldung.hexagonalarchitecture.corejava;

import com.baeldung.hexagonalarchitecture.corejava.adapter.driving.ExchangeRatesApplicationAdapter;
import com.baeldung.hexagonalarchitecture.corejava.parser.ExchangeRateDateParser;
import com.baeldung.hexagonalarchitecture.corejava.port.driving.ExchangeRatesApplicationPort;

// represents application layer
public class ExchangeRatesApplication {

    private ExchangeRatesApplicationPort exchangeRatesApplicationPort;

    private ExchangeRateDateParser exchangeRateDateParser;

    public ExchangeRatesApplication() {
        exchangeRatesApplicationPort = new ExchangeRatesApplicationAdapter();
        exchangeRateDateParser = new ExchangeRateDateParser();
    }

    public String execute(String[] commandArguments) {
        if (commandArguments.length == 0) {
            throw new IllegalArgumentException(
                    String.format(
                            "Please provide one of these commands: \n"
                                    + " - 'currencies' - to list all available currencies\n"
                                    + " - 'get $currencyCode $%s - to get exchange rate for given currency and date",
                            exchangeRateDateParser.getExchangeRateDateFormat()));
        }
        switch (commandArguments[0]) {
            case "currencies":
                return loadAllAvailableCurrencies();
            case "get":
                return printExchangeRateForCurrencyAndDate(commandArguments);
            default:
                throw new IllegalArgumentException(String.format("Unknown command provided: %s", commandArguments[0]));
        }
    }

    private String printExchangeRateForCurrencyAndDate(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException(
                    String.format("Incorrect 'get' command provided. Expected command format is 'get $currencyCode $%s",
                            exchangeRateDateParser.getExchangeRateDateFormat()));
        }

        String dateTime = args[2];
        String currencyCode = args[1];

        return String.format("%s %s %f",
                currencyCode,
                dateTime,
                exchangeRatesApplicationPort.getCurrencyExchangeRatesForDate(
                        currencyCode,
                        exchangeRateDateParser.parseExchangeRateDate(dateTime)));
    }

    private String loadAllAvailableCurrencies() {
        return String.join("\n", exchangeRatesApplicationPort.loadAvailableCurrencies());
    }
}
