package com.baeldung.teng.invoicing.domain;

import java.math.BigDecimal;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Item {

    private final String name;

    private final BigDecimal price;

    private final BigDecimal quantity;

    private final BigDecimal vat;

    public Item(String name, BigDecimal price, BigDecimal quantity, BigDecimal vat) {
        this.name = requireNonNull(name);
        this.price = requireNonNull(price);
        this.quantity = requireNonNull(quantity);
        this.vat = requireNonNull(vat);
    }

    public String getName() { return name; }

    public BigDecimal getPrice() { return price; }

    public BigDecimal getQuantity() { return quantity; }

    public BigDecimal getVat() { return vat; }

    @Override
    public String toString() { return name; }

    @Override
    public boolean equals(Object o) {
        return this == o || o != null && o.getClass() == getClass() && Objects.equals(((Item) o).name, name);
    }

    @Override
    public int hashCode() { return Objects.hash(name); }
}
