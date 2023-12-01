package org.baeldung.cassandra.inquery;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import org.baeldung.cassandra.inquery.model.Product;
import org.baeldung.cassandra.inquery.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This live test requires a running Docker instance so that a Cassandra container can be created
 */
@Testcontainers
@SpringBootTest
class ProductRepositoryNestedLiveTest {

    private static final String KEYSPACE_NAME = "mynamespace";

    @Container
    private static final CassandraContainer cassandra = (CassandraContainer) new CassandraContainer("cassandra:3.11.2")
        .withExposedPorts(9042);

    @BeforeAll
    static void setupCassandraConnectionProperties() {
        System.setProperty("spring.data.cassandra.keyspace-name", KEYSPACE_NAME);
        System.setProperty("spring.data.cassandra.contact-points", cassandra.getHost());
        System.setProperty("spring.data.cassandra.port", String.valueOf(cassandra.getMappedPort(9042)));
        createKeyspace(cassandra.getCluster());
    }

    static void createKeyspace(Cluster cluster) {
        try (Session session = cluster.connect()) {
            session.execute("CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE_NAME + " WITH replication = \n" +
                    "{'class':'SimpleStrategy','replication_factor':'1'};");
        }
    }

    @Nested
    class ApplicationContextLiveTest {
        @Test
        void givenCassandraContainer_whenSpringContextIsBootstrapped_thenContainerIsRunningWithNoExceptions() {
            assertThat(cassandra.isRunning()).isTrue();
        }
    }

    @Nested
    class ProductRepositoryLiveTest {

        @Autowired
        private ProductRepository productRepository;

        @Test
        void givenValidProductsIsFetched_whenFindByProductIdsIsCalled_thenProductIsReturned() {
            UUID productId1 = UUIDs.timeBased();
            UUID productId2 = UUIDs.timeBased();
            UUID productId3 = UUIDs.timeBased();
            Product product1 = new Product(productId1, "Apple", "Apple v1", 12.5);
            Product product2 = new Product(productId2, "Apple v2", "Apple v2", 15.5);
            Product product3 = new Product(productId3, "Banana", "Banana v1", 5.5);
            Product product4 = new Product(productId3, "Banana v2", "Banana v2", 15.5);

            productRepository.saveAll(List.of(product1, product2, product3, product4));

            List<Product> existingProducts = productRepository.findByProductIds(List.of(productId1, productId2));
            assertEquals(2, existingProducts.size());
            assertTrue(existingProducts.contains(product1));
            assertTrue(existingProducts.contains(product2));
        }

        @Test
        void givenExistingProducts_whenFindByIdAndNamesIsCalled_thenProductIsReturned() {
            UUID productId1 = UUIDs.timeBased();
            UUID productId2 = UUIDs.timeBased();
            Product product1 = new Product(productId1, "Apple", "Apple v1", 12.5);
            Product product2 = new Product(productId1, "Apple v2", "Apple v2", 15.5);
            Product product3 = new Product(productId2, "Banana", "Banana v1", 5.5);
            Product product4 = new Product(productId2, "Banana v2", "Banana v2", 15.5);

            productRepository.saveAll(List.of(product1, product2, product3, product4));

            List<Product> existingProducts = productRepository.findByProductIdAndNames(productId1,
                List.of(product1.getProductName(), product2.getProductName()));
            assertEquals(2, existingProducts.size());
            assertTrue(existingProducts.contains(product1));
            assertTrue(existingProducts.contains(product2));
        }

        @Test
        void givenNonExistingProductName_whenFindByIdAndNamesIsCalled_thenProductIsReturned() {
            UUID productId1 = UUIDs.timeBased();
            UUID productId2 = UUIDs.timeBased();
            Product product1 = new Product(productId1, "Apple", "Apple v1", 12.5);
            Product product2 = new Product(productId1, "Apple v2", "Apple v2", 15.5);
            Product product3 = new Product(productId2, "Banana", "Banana v1", 5.5);
            Product product4 = new Product(productId2, "Banana v2", "Banana v2", 15.5);

            productRepository.saveAll(List.of(product1, product2, product4));

            List<Product> existingProducts = productRepository.findByProductIdAndNames(productId1,
                    List.of(product3.getProductName()));
            assertEquals(0, existingProducts.size());
        }
    }
}
