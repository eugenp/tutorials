package com.baeldung.teng.invoicing.domain;

import java.math.BigDecimal;
import java.util.*;

import static java.math.RoundingMode.HALF_UP;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class Invoice {

    private final String id;

    private final Date date;

    private final Customer customer;

    private final List<Item> items = new ArrayList<>();

    public Invoice() { this("N/A", new Date(), null); }

    public Invoice(String id, Date date, Customer customer) {
        this.id = requireNonNull(id);
        this.date = requireNonNull(date);
        this.customer = requireNonNull(customer);
    }

    public String getId() { return id; }

    public Date getDate() { return date; }

    public Customer getCustomer() { return customer; }

    public List<Item> getItems() { return unmodifiableList(items); }

    public Invoice setItems(Collection<Item> items) {
        this.items.clear();
        if (items != null) {
            this.items.addAll(items.stream().filter(Objects::nonNull).collect(toList()));
        }
        return this;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Item item : items) {
            totalPrice = totalPrice.add(item.getTotalPrice());
        }

        return totalPrice.setScale(2, HALF_UP);
    }

    @Override
    public String toString() { return "Invoice #" + id; }

    @Override
    public boolean equals(Object o) {
        return this == o || o != null && o.getClass() == getClass() && Objects.equals(((Invoice) o).id, id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
