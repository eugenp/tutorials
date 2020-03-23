package com.baeldung.hexagonalarchitecture.corejava.adapter.driven;


import com.baeldung.hexagonalarchitecture.corejava.domain.ExchangeRate;
import com.baeldung.hexagonalarchitecture.corejava.port.driven.ExchangeRateInfrastructurePort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MonthlyExchangeRateInfrastructureAdapter implements ExchangeRateInfrastructurePort {

    private SimpleDateFormat format;

    public MonthlyExchangeRateInfrastructureAdapter() {
        this.format = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
    }

    // todo temp solution, just example
    @Override
    public List<ExchangeRate> loadExchangeRates() {

        // todo load it from a file
        try {
            return Arrays.asList(
                    new ExchangeRate("ARS", format.parse("12-jan-2012"), 63.71D),
                    new ExchangeRate("EUR", format.parse("24-jul-2018"), 0.93D));
        } catch (ParseException e) {
            // todo temp solution
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
