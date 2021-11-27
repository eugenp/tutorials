package com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.ports;

import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.Stock;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.ports.exceptions.ProductNotFoundException;

/**
 * Port that will help external services to ask for the Stock of a product
 * 
 * @author vsempere
 *
 */
public interface StockRequestPortIF {
    public Stock getStockByProductCode(String productCode) throws ProductNotFoundException;
}
