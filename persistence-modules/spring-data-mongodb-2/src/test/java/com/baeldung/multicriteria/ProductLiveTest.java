package com.baeldung.multicriteria;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.multicriteria.config.MongoTestConfig;
import com.baeldung.multicriteria.repository.CustomProductRepository;
import com.baeldung.multicriteria.repository.ProductRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MongoTestConfig.class)
public class ProductLiveTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomProductRepository customProductRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @BeforeEach
    void setUp() {
        if (mongoTemplate == null) {
            throw new IllegalStateException("MongoTemplate is not initialized!");
        }
        // Clean up the database before each test
        mongoTemplate.dropCollection(Product.class);

        // Insert test data
        Product product1 = new Product("MacBook Pro M3", 1500, "Laptop", true);
        Product product2 = new Product("MacBook Air M2", 1000, "Laptop", false);
        Product product3 = new Product("iPhone 13", 800, "Phone", true);
        mongoTemplate.save(product1);
        mongoTemplate.save(product2);
        mongoTemplate.save(product3);
    }

    @Test
    void givenProductNameCategoryAvailabilityAndPrice_whenFindUsingAndOperator_thenReturnsMatchingProduct() {
        List<Product> actualProducts = productService.findProductsUsingAndOperator("MacBook Pro M3", 1000, "Laptop", true);
        assertThat(actualProducts).hasSize(1);
        assertThat(actualProducts.get(0).getName()).isEqualTo("MacBook Pro M3");
    }

    @Test
    void givenProductCategoryAndPrice_whenFindUsingOrOperator_thenReturnsMatchingProduct() {
        List<Product> actualProducts = productService.findProductsUsingOrOperator("Laptop", 1000);
        assertThat(actualProducts).hasSize(2);
    }

    @Test
    void givenMultipleCriteria_whenFindUsingAndAndOrOperator_thenReturnsMatchingProduct() {
        List<Product> actualProducts = productService.findProductsUsingAndOperatorAndOrOperator("Laptop", 1000, "MacBook Pro M3", true);
        assertThat(actualProducts).hasSize(1);
        assertThat(actualProducts.get(0).getName()).isEqualTo("MacBook Pro M3");
    }

    @Test
    void givenProductNameCategoryAvailabilityAndPrice_whenFindUsingChainMethod_thenReturnsMatchingProduct() {
        List<Product> actualProducts = productService.findProductsUsingChainMethod("MacBook Pro M3", 1000, "Laptop",  true);
        assertThat(actualProducts).hasSize(1);
        assertThat(actualProducts.get(0).getName()).isEqualTo("MacBook Pro M3");
    }

    @Test
    void givenProductNameCategoryAvailabilityAndPrice_whenFindUsingQueryDSLWithAndCondition_thenReturnsMatchingProduct() {
        List<Product> actualProducts = productService.findProductsUsingQueryDSLWithAndCondition("Laptop", true, "MacBook Pro M3", 1000);
        assertThat(actualProducts).hasSize(1);
        assertThat(actualProducts.get(0).getName()).isEqualTo("MacBook Pro M3");
    }

    @Test
    void givenProductNameCategoryAvailabilityAndPrice_whenFindUsingQueryDSLWithOrCondition_thenReturnsMatchingProduct() {
        List<Product> actualProducts = productService.findProductsUsingQueryDSLWithOrCondition("Laptop", "MacBook Pro M3", 800);
        assertThat(actualProducts).hasSize(2);
    }

    @Test
    void givenProductNameCategoryAvailabilityAndPrice_whenFindUsingQueryDSLWithAndOrCondition_thenReturnsMatchingProduct () {
        List<Product> actualProducts = productService.findProductsUsingQueryDSLWithAndOrCondition("Laptop", true, "MacBook Pro M3", 1000);
        assertThat(actualProducts).hasSize(1);
        assertThat(actualProducts.get(0).getName()).isEqualTo("MacBook Pro M3");
    }

    @Test
    void givenProductNameCategoryAvailabilityAndPrice_whenFindUsingQueryAnnotation_thenReturnsMatchingProducts() {
        List<Product> actualProducts = productRepository.findProductsByNamePriceCategoryAndAvailability("MacBook Pro M3", 1000, "Laptop",  true);
        assertThat(actualProducts).hasSize(1);
        assertThat(actualProducts.get(0).getName()).isEqualTo("MacBook Pro M3");
    }

    @Test
    void givenCategoryAndAvailabilityOrPrice_whenFindUsingQueryAnnotation_thenReturnsMatchingProducts() {
        List<Product> actualProducts = productRepository.findProductsByCategoryAndAvailabilityOrPrice("Laptop", false, 600);
        assertThat(actualProducts).hasSize(3);
    }
}
