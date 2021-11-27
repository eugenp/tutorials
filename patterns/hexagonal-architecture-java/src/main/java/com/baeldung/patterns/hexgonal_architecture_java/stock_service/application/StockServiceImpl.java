package com.baeldung.patterns.hexgonal_architecture_java.stock_service.application;

import java.util.List;

import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.Product;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.ProductStockLevel;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.Stock;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.StockLevel;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.factories.StockFactoryIF;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.ports.DatabasePortIF;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.ports.exceptions.ProductNotFoundException;

/**
 * Implementation of the application layer. This implementation will contain all the business logic and there won't be references to external dependencies.
 * 
 * @author vsempere
 *
 */
public class StockServiceImpl implements StockServiceIF {

    private DatabasePortIF portDatabase;
    private StockFactoryIF stockFactory;

    /**
     * The constructor will receive all the required interfaces. 
     * @param portDatabase {@link DatabasePortIF}
     * @param stockFactory {@link StockFactoryIF}
     */
    public StockServiceImpl(DatabasePortIF portDatabase, StockFactoryIF stockFactory) {
        this.portDatabase = portDatabase;
        this.stockFactory = stockFactory;
    }

    @Override
    public Stock getStockByProductCode(String productCode) throws ProductNotFoundException {
        Product product = portDatabase.getProductByCode(productCode);
        StockLevel level = determineProductStockLevel(product);
        return stockFactory.getInstance(product, level);
    }

    private StockLevel determineProductStockLevel(Product product) {
        try {
            Long max = 0l;

            String productCode = product.getCode();
            List<ProductStockLevel> stockLevels = portDatabase.getProductStockLevelsByCode(productCode);
            for (ProductStockLevel productStockLevel : stockLevels) {
                if (productMatchesStockLevel(productStockLevel, product)) {
                    return productStockLevel.getLevel();
                }
                if (productStockLevel.getMaxStock() > max) {
                    max = productStockLevel.getMaxStock();
                }
            }
            if (product.getStock() > max) {
                return StockLevel.OVER_STOCK;
            } else {
                return StockLevel.UNDEFINED;
            }

        } catch (ProductNotFoundException e) {
            return StockLevel.UNDEFINED;

        } catch (Exception e) {
            return StockLevel.UNDEFINED;
        }

    }

    private boolean productMatchesStockLevel(ProductStockLevel productStockLevel, Product product) {
        return productStockLevel.getMinStock() <= product.getStock() && productStockLevel.getMaxStock() >= product.getStock();
    }
}
