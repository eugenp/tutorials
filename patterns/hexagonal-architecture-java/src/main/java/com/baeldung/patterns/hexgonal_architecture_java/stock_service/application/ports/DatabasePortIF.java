package com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.ports;


import java.util.List;

import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.Product;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.ProductStockLevel;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.ports.exceptions.ProductNotFoundException;

public interface DatabasePortIF {
    public Product getProductByCode(String productCode) throws ProductNotFoundException;

    public List<ProductStockLevel> getProductStockLevelsByCode(String productCode) throws ProductNotFoundException;
}
