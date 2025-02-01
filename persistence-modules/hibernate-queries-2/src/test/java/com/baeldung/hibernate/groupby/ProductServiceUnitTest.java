package com.baeldung.hibernate.groupby;

import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductServiceUnitTest {

    private ProductService productService;

    @BeforeAll
    public void setup() {
        productService = new ProductService();

        // Insert sample data
        try (Session session = productService.getSessionFactory().openSession()) {
            session.beginTransaction();

            Product product1 = new Product("Product1", "Electronics", 100.0);
            Product product2 = new Product("Product2", "Electronics", 150.0);
            Product product3 = new Product("Product3", "Furniture", 200.0);

            session.persist(product1);
            session.persist(product2);
            session.persist(product3);

            session.getTransaction().commit();
        }
    }

    @Test
    public void whenGroupedByCategory_thenReturnTotalPrice() {
        List<Object[]> results = productService.getTotalPricePerCategory();

        Assertions.assertEquals(2, results.size());

        results.forEach(record -> {
            String category = (String) record[0];
            Double totalPrice = (Double) record[1];

            if ("Electronics".equals(category)) {
                Assertions.assertEquals(250.0, totalPrice);
            } else if ("Furniture".equals(category)) {
                Assertions.assertEquals(200.0, totalPrice);
            }
        });
    }
}
