package com.baeldung.axon.coreapi.queries;

public class TotalProductsShippedQuery {
    private final String productId;

    public TotalProductsShippedQuery(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }
}
