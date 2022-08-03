package com.baeldung.axon.querymodel;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest(properties = { "spring.mongodb.embedded.version=5.0.6" })
public class MongoOrdersEventHandlerIntegrationTest extends AbstractOrdersEventHandlerTest{

    @Autowired
    MongoClient mongoClient;

    @Override
    protected OrdersEventHandler getHandler() {
        mongoClient.getDatabase("axonframework").drop();
        return new MongoOrdersEventHandler(mongoClient, emitter);
    }
}
