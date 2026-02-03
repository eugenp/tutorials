package com.baeldung.jackson.pojomapping;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.transitions.Mongod;
import de.flapdoodle.embed.mongo.transitions.RunningMongodProcess;
import de.flapdoodle.reverse.TransitionWalker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceLiveTest {

    private TransitionWalker.ReachedState<RunningMongodProcess> mongodbProcess;
    private MongoClient mongoClient;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        mongodbProcess = Mongod.instance().start(Version.Main.V5_0);
        var serverAddress = mongodbProcess.current().getServerAddress();

        mongoClient = MongoClients.create(String.format("mongodb://%s:%d",
          serverAddress.getHost(), serverAddress.getPort()));
        productService = new ProductService(mongoClient.getDatabase("testdb"));
    }

    @AfterEach
    void tearDown() {
        if (mongoClient != null) {
            mongoClient.close();
        }
        if (mongodbProcess != null) {
            mongodbProcess.close();
        }
    }

    @Test
    void whenSavingProduct_thenProductIsPersisted() {
        Product product = new Product("Laptop", 999.99);

        productService.save(product);

        assertNotNull(product.getId());
        assertEquals(1, productService.count());
    }

    @Test
    void whenSavingProductWithAllFields_thenAllFieldsArePersisted() {
        Product product = new Product("Full Product", 199.99);

        productService.save(product);
        Product foundProduct = productService.findById(product.getId());

        assertNotNull(foundProduct.getId());
        assertEquals("Full Product", foundProduct.getName());
        assertEquals(199.99, foundProduct.getPrice(), 0.01);
    }
}
