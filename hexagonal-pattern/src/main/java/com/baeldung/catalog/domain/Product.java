package com.baeldung.catalog.domain;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class Product {
    private String id;
    private String shortName;
    private Set<String> tags = new LinkedHashSet<>();
    private Set<ProductPrice> productPrices = new TreeSet<>(ProductPrice.dateComparator());

    public Product(String id, String shortName) {
        super();
        this.id = id;
        this.shortName = shortName;
    }

    public void addPrice(ProductPrice price) {
        validatePriceOverlap();
    }

    public Optional<ProductPrice> resolvePriceForDate(ZonedDateTime dateTime) {
        return productPrices.stream()
            .filter(e -> e.matches(dateTime))
            .findFirst();
    }

    public String getId() {
        return id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Set<String> getTags() {
        return tags;
    }

    public Set<ProductPrice> getProductPrices() {
        return Collections.unmodifiableSet(productPrices);
    }

    private void validatePriceOverlap() {
        // TODO validates the set for price range ambiguity
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortName, tags, productPrices);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(shortName, product.shortName) && Objects.equals(tags, product.tags) && Objects.equals(productPrices, product.productPrices);
    }
}
