package com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model;

import java.io.Serializable;

public class Stock implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 7217103337913346199L;
    private String productCode;
    private Long stock;
    private StockLevel stockLevel;

    public Stock() {
        super();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public StockLevel getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(StockLevel stockLevel) {
        this.stockLevel = stockLevel;
    }

    @Override
    public String toString() {
        return "Stock [productCode=" + productCode + ", stock=" + stock + ", stockLevel=" + stockLevel + "]";
    }

}
