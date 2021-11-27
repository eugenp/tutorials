package com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model;

import java.io.Serializable;

public class ProductStockLevel implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -370960064256395879L;
    private StockLevel level;
    private Long minStock;
    private Long maxStock;

    public ProductStockLevel() {
    }

    public StockLevel getLevel() {
        return level;
    }

    public void setLevel(StockLevel level) {
        this.level = level;
    }

    public Long getMinStock() {
        return minStock;
    }

    public void setMinStock(Long minStock) {
        this.minStock = minStock;
    }

    public Long getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(Long maxStock) {
        this.maxStock = maxStock;
    }

}
