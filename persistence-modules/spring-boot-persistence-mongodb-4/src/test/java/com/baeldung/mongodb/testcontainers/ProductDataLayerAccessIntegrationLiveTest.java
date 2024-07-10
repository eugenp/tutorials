package com.baeldung.mongodb.testcontainers;

import com.baeldung.testcontainers.model.Product;
import com.baeldung.testcontainers.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDataLayerAccessIntegrationLiveTest extends AbstractBaseIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void givenProductRepository_whenSaveAndRetrieveProduct_thenOK() {
        Product product = new Product("Milk", "1L Milk", 10);

        Product createdProduct = productRepository.save(product);
        Optional<Product> optionalProduct = productRepository.findById(createdProduct.getId());

        assertThat(optionalProduct.isPresent()).isTrue();

        Product retrievedProduct = optionalProduct.get();
        assertThat(retrievedProduct.getId()).isEqualTo(product.getId());
    }

    @Test
    public void givenProductRepository_whenFindByName_thenOK() {
        Product product = new Product("Apple", "Fruit", 10);

        Product createdProduct = productRepository.save(product);
        Optional<Product> optionalProduct = productRepository.findByName(createdProduct.getName());

        assertThat(optionalProduct.isPresent()).isTrue();

        Product retrievedProduct = optionalProduct.get();
        assertThat(retrievedProduct.getId()).isEqualTo(product.getId());
    }

}
