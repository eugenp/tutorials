package com.baeldung.axon.querymodel;

import com.mongodb.client.MongoClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
public class MongoOrdersEventHandlerUnitTest extends AbstractOrdersEventHandlerUnitTest {

    @Autowired
    MongoClient mongoClient;

    @Override
    protected OrdersEventHandler getHandler() {
        mongoClient.getDatabase("axonframework")
          .drop();
        return new MongoOrdersEventHandler(mongoClient, emitter);
    }
}
