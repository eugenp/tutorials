package com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.factories;

import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.Product;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.Stock;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.StockLevel;

public interface StockFactoryIF {
    public Stock getInstance(Product product, StockLevel level);
}
