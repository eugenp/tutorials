package com.baeldung.axon.querymodel;

import com.mongodb.client.MongoClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

@DataMongoTest
public class MongoOrdersEventHandlerLiveTest extends AbstractOrdersEventHandlerUnitTest {

    @Autowired
    MongoClient mongoClient;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0").withExposedPorts(27017);

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {

        mongoDBContainer.start();
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }



    @Override
    protected OrdersEventHandler getHandler() {
        mongoClient.getDatabase("axonframework")
          .drop();
        return new MongoOrdersEventHandler(mongoClient, emitter);
    }
}
