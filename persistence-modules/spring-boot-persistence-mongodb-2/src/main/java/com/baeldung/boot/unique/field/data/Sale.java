package com.baeldung.boot.unique.field.data;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Sale {
    @Indexed(unique = true)
    private SaleId saleId;

    private Double value;

    public Sale() {
    }

    public Sale(SaleId saleId) {
        super();
        this.saleId = saleId;
    }

    public SaleId getSaleId() {
        return saleId;
    }

    public void setSaleId(SaleId saleId) {
        this.saleId = saleId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
