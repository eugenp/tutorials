package com.baeldung.hexagonal.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Currency;

@Getter
public class ExchangeRate implements ExchangeRateDomain {

    private final LocalDate date;
    private final Currency from;
    private final Currency to;
    private final BigDecimal rate;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ExchangeRate(@JsonProperty("date") LocalDate date, @JsonProperty("from") Currency from,
                        @JsonProperty("to") Currency to, @JsonProperty("rate") BigDecimal rate) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public ExchangeRate reverse() {
        return new ExchangeRate(date, to, from, BigDecimal.ONE.divide(rate, RoundingMode.HALF_EVEN));
    }
}
