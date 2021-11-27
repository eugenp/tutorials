package com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.factories;

import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.Product;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.Stock;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.StockLevel;

public class StockFactory implements StockFactoryIF {

    @Override
    public Stock getInstance(Product product, StockLevel level) {
        Stock stock = new Stock();
        stock.setProductCode(product.getCode());
        stock.setStock(product.getStock());
        stock.setStockLevel(level);
        return stock;
    }

}
