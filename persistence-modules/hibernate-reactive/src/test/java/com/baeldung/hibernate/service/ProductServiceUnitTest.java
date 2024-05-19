package com.baeldung.hibernate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.hibernate.entities.Product;
import com.baeldung.hibernate.repository.ProductRepository;

@SpringBootTest
@AutoConfigureWebTestClient
public class ProductControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll()
            .then(productRepository.save(new Product(null, "Product 1", "Category 1", 10.0)))
            .then(productRepository.save(new Product(null, "Product 2", "Category 1", 15.0)))
            .block();
    }

    @Test
    void testGetProductById() {
        webTestClient.get().uri("/products/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody(Product.class)
            .value(product -> {
                assertEquals("Product 1", product.getName());
                assertEquals("Category 1", product.getCategory());
                assertEquals(10.0, product.getPrice());
            });
    }

    @Test
    void testGetAllProducts() {
        webTestClient.get().uri("/products")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Product.class)
            .hasSize(2)
            .value(products -> {
                assertEquals("Product 1", products.get(0).getName());
                assertEquals("Product 2", products.get(1).getName());
            });
    }

    @Test
    void testCreateProduct() {
        Product newProduct = new Product(null, "Product 3", "Category 2", 20.0);

        webTestClient.post().uri("/products")
            .bodyValue(newProduct)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Product.class)
            .value(product -> {
                assertNotNull(product.getId());
                assertEquals("Product 3", product.getName());
            });

        webTestClient.get().uri("/products")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Product.class)
            .hasSize(3);
    }

    @Test
    void testDeleteProduct() {
        webTestClient.delete().uri("/products/1")
            .exchange()
            .expectStatus().isOk();

        webTestClient.get().uri("/products/1")
            .exchange()
            .expectStatus().isNotFound();
    }
}
