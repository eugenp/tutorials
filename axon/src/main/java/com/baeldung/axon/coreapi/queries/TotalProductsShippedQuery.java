package com.baeldung.axon.coreapi.queries;

import java.util.Objects;

public class TotalProductsShippedQuery {

    private final String productId;

    public TotalProductsShippedQuery(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TotalProductsShippedQuery that = (TotalProductsShippedQuery) o;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return "TotalProductsShippedQuery{" + "productId='" + productId + '\'' + '}';
    }
}
