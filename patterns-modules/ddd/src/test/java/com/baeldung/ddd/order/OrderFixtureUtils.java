package com.baeldung.ddd.order;

import java.util.Arrays;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class OrderFixtureUtils {
    public static List<OrderLine> anyOrderLines() {
        return Arrays.asList(new OrderLine(new Product(Money.of(CurrencyUnit.USD, 100)), 1));
    }

    public static List<OrderLine> orderLineItemsWorthNDollars(int totalCost) {
        return Arrays.asList(new OrderLine(new Product(Money.of(CurrencyUnit.USD, totalCost)), 1));
    }
}
