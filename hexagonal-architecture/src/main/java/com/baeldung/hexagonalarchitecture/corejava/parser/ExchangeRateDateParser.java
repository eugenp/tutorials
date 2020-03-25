package com.baeldung.hexagonalarchitecture.corejava.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExchangeRateDateParser {

    private static final String EXCHANGE_RATE_DATE_FORMAT = "dd-MM-yyyy";

    private final SimpleDateFormat formatter = new SimpleDateFormat(EXCHANGE_RATE_DATE_FORMAT);

    public Date parseExchangeRateDate(String dateTime) {
        try {
            return formatter.parse(dateTime);
        } catch (ParseException e) {
            throw new IllegalArgumentException(
                    String.format("Exchange rate date expected in different format: %s", EXCHANGE_RATE_DATE_FORMAT));
        }
    }

    public String getExchangeRateDateFormat() {
        return EXCHANGE_RATE_DATE_FORMAT;
    }
}
