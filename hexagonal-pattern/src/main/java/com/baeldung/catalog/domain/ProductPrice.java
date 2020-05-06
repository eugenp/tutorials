package com.baeldung.catalog.domain;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Objects;

import javax.validation.constraints.NotNull;

public class ProductPrice {
    private static final long DEFAULT_OFFSET_YEARS = 10;

    private BigDecimal price;
    private ZonedDateTime start;
    private ZonedDateTime end;

    public ProductPrice(BigDecimal price) {
        this.price = price;
        this.start = ZonedDateTime.now();
        this.end = start.plusYears(DEFAULT_OFFSET_YEARS);
    }

    public ProductPrice(BigDecimal price, @NotNull ZonedDateTime start, @NotNull ZonedDateTime end) {
        super();
        this.price = price;
        this.start = start;
        this.end = end;
        validateDateRange();
    }

    public boolean matches(ZonedDateTime dateTime) {
        return getStart().isBefore(dateTime) && getEnd().isAfter(dateTime);
    }

    private void validateDateRange() {
        if (start.isAfter(end)) {
            throw new InvalidPriceRangeException();
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, start, end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(price, that.price) && Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    public static Comparator<ProductPrice> dateComparator() {
        return (ProductPrice o1, ProductPrice o2) -> o1.getStart()
            .compareTo(o2.getStart());
    }

    public static Comparator<ProductPrice> priceComparator() {
        return (ProductPrice o1, ProductPrice o2) -> o1.getPrice()
            .compareTo(o2.getPrice());
    }
}
