package com.baeldung.teng.invoicing.domain;

import java.math.BigDecimal;
import java.util.Objects;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;
import static java.util.Objects.requireNonNull;

public class Item {

    private final String name;

    private final BigDecimal unitPrice;

    private final BigDecimal quantity;

    private final BigDecimal vat;

    public Item(String name, BigDecimal unitPrice, BigDecimal quantity, BigDecimal vat) {
        this.name = requireNonNull(name);
        this.unitPrice = requireNonNull(unitPrice);
        this.quantity = requireNonNull(quantity);
        this.vat = requireNonNull(vat);
    }

    public String getName() { return name; }

    public BigDecimal getUnitPrice() { return unitPrice; }

    public BigDecimal getQuantity() { return quantity; }

    public BigDecimal getVat() { return vat; }

    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(quantity).multiply(ONE.add(vat.divide(valueOf(100), 2, HALF_UP)))
                        .setScale(2, HALF_UP);
    }

    @Override
    public String toString() { return name; }

    @Override
    public boolean equals(Object o) {
        return this == o || o != null && o.getClass() == getClass() && Objects.equals(((Item) o).name, name);
    }

    @Override
    public int hashCode() { return Objects.hash(name); }
}
