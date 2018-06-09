package com.baeldung.reactive.template;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    String symbol;
    String name;
    BigDecimal currentPrice;
    long lastUpdated;
}
