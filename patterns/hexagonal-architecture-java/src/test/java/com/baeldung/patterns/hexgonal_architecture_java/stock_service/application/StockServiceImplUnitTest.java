package com.baeldung.patterns.hexgonal_architecture_java.stock_service.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.Product;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.ProductStockLevel;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.Stock;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.StockLevel;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.factories.StockFactory;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.model.factories.StockFactoryIF;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.ports.DatabasePortIF;
import com.baeldung.patterns.hexgonal_architecture_java.stock_service.application.ports.exceptions.ProductNotFoundException;

class StockServiceImplUnitTest {

    private static StockServiceIF toTest;
    private static DatabasePortIF portDatabase;

    @BeforeAll
    static void setUp() {
        portDatabase = Mockito.mock(DatabasePortIF.class);
        StockFactoryIF stockFactory = new StockFactory();
        toTest = new StockServiceImpl(portDatabase, stockFactory);
    }

    @Test
    void givenUnknownProductCode_whenGetStockByProductCode_thenThrowsProductNotFoundException() {
        // given
        String nonExistingProductCode = "NON EXISTING CODE";
        ProductNotFoundException notFoundException = new ProductNotFoundException();
        notFoundException.setProductCode(nonExistingProductCode);
        Mockito.when(portDatabase.getProductByCode(ArgumentMatchers.matches(nonExistingProductCode)))
            .thenThrow(notFoundException);

        // when
        try {
            toTest.getStockByProductCode(nonExistingProductCode);
            fail("Expected non existing product code exception");

        } catch (ProductNotFoundException e) {
            // then
            assertEquals(nonExistingProductCode, e.getProductCode());
        }
    }

    @Test
    void whenGetStockByProductCode_thenGetUnknownStockLevel() {
        // given
        String productCode = "productCode";
        Product product = new Product();
        product.setCode(productCode);
        product.setName("Product name");
        product.setStock(-1l);
        Mockito.when(portDatabase.getProductByCode(ArgumentMatchers.matches(productCode)))
            .thenReturn(product);
        ProductStockLevel redLevel = new ProductStockLevel();
        redLevel.setLevel(StockLevel.RED);
        redLevel.setMinStock(0l);
        redLevel.setMaxStock(99l);
        List<ProductStockLevel> productStockLevels = Arrays.asList(redLevel);
        Mockito.when(portDatabase.getProductStockLevelsByCode(ArgumentMatchers.matches(productCode)))
            .thenReturn(productStockLevels);

        // when
        Stock stock = toTest.getStockByProductCode(productCode);

        // then
        assertEquals(productCode, stock.getProductCode());
        assertEquals(product.getStock(), stock.getStock());
        assertEquals(StockLevel.UNDEFINED, stock.getStockLevel());
    }

    @Test
    void givenMinimumStockLevel_whenGetStockByProductCode_thenGetStockLevel() {
        // given
        String productCode = "productCode";
        Product product = new Product();
        product.setCode(productCode);
        product.setName("Product name");
        product.setStock(0l);
        Mockito.when(portDatabase.getProductByCode(ArgumentMatchers.matches(productCode)))
            .thenReturn(product);
        ProductStockLevel redLevel = new ProductStockLevel();
        redLevel.setLevel(StockLevel.RED);
        redLevel.setMinStock(0l);
        redLevel.setMaxStock(99l);
        List<ProductStockLevel> productStockLevels = Arrays.asList(redLevel);
        Mockito.when(portDatabase.getProductStockLevelsByCode(ArgumentMatchers.matches(productCode)))
            .thenReturn(productStockLevels);

        // when
        Stock stock = toTest.getStockByProductCode(productCode);

        // then
        assertEquals(productCode, stock.getProductCode());
        assertEquals(product.getStock(), stock.getStock());
        assertEquals(StockLevel.RED, stock.getStockLevel());
    }

    @Test
    void givenMaximumtockLevel_whenGetStockByProductCode_thenGetStockLevel() {
        // given
        String productCode = "productCode";
        Product product = new Product();
        product.setCode(productCode);
        product.setName("Product name");
        product.setStock(99l);
        Mockito.when(portDatabase.getProductByCode(ArgumentMatchers.matches(productCode)))
            .thenReturn(product);
        ProductStockLevel redLevel = new ProductStockLevel();
        redLevel.setLevel(StockLevel.RED);
        redLevel.setMinStock(0l);
        redLevel.setMaxStock(99l);
        List<ProductStockLevel> productStockLevels = Arrays.asList(redLevel);
        Mockito.when(portDatabase.getProductStockLevelsByCode(productCode))
            .thenReturn(productStockLevels);

        // when
        Stock stock = toTest.getStockByProductCode(productCode);

        // then
        assertEquals(productCode, stock.getProductCode());
        assertEquals(product.getStock(), stock.getStock());
        assertEquals(StockLevel.RED, stock.getStockLevel());
    }

    @Test
    void whenGetStockByProductCode_thenGetOverStockLevel() {
        // given
        String productCode = "productCode";
        Product product = new Product();
        product.setCode(productCode);
        product.setName("Product name");
        product.setStock(100l);
        Mockito.when(portDatabase.getProductByCode(ArgumentMatchers.matches(productCode)))
            .thenReturn(product);
        ProductStockLevel redLevel = new ProductStockLevel();
        redLevel.setLevel(StockLevel.RED);
        redLevel.setMinStock(0l);
        redLevel.setMaxStock(99l);
        List<ProductStockLevel> productStockLevels = Arrays.asList(redLevel);
        Mockito.when(portDatabase.getProductStockLevelsByCode(productCode))
            .thenReturn(productStockLevels);

        // when
        Stock stock = toTest.getStockByProductCode(productCode);

        // then
        assertEquals(productCode, stock.getProductCode());
        assertEquals(product.getStock(), stock.getStock());
        assertEquals(StockLevel.OVER_STOCK, stock.getStockLevel());
    }

    @Test
    void givenProductCodeWithoutStockLevel_whenGetStockByProductCode_thenGetStockWithUndefinedStockLevel() {
        // given
        String productCode = "productCode";
        Product product = new Product();
        product.setCode(productCode);
        product.setName("Product name");
        product.setStock(100l);      
        Mockito.when(portDatabase.getProductByCode(ArgumentMatchers.matches(productCode)))
            .thenReturn(product);
        ProductNotFoundException notFoundException = new ProductNotFoundException();
        notFoundException.setProductCode(productCode);
        Mockito.when(portDatabase.getProductStockLevelsByCode(productCode))
            .thenThrow(notFoundException);

        // when
        Stock stock = toTest.getStockByProductCode(productCode);

        // then
        assertEquals(productCode, stock.getProductCode());
        assertEquals(product.getStock(), stock.getStock());
        assertEquals(StockLevel.UNDEFINED, stock.getStockLevel());
    }

}
