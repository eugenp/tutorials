package com.example.hexagonalarchitecture.kitchenassistant.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Value
public class Order {

    @Getter
    BigDecimal cost;

    @Getter
    String note;

    @Getter
    List<String> items;
}
