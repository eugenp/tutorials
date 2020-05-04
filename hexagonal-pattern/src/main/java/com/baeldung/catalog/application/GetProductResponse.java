package com.baeldung.catalog.application;

import java.util.Set;

import com.baeldung.catalog.domain.ProductPrice;

public class GetProductResponse {
    private String id;
    private String shortDescription;
    private Set<ProductPrice> prices;
    
    /**
     * For serialization
     */
    public GetProductResponse() {}
   
    public GetProductResponse(String id, String shortDescription, Set<ProductPrice> prices) {
        super();
        this.id = id;
        this.shortDescription = shortDescription;
        this.prices = prices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Set<ProductPrice> getPrices() {
        return prices;
    }

    public void setPrices(Set<ProductPrice> prices) {
        this.prices = prices;
    }
}
