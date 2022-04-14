package com.baeldung.mongo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

public class ConnectionCheckLiveTest {

    private static MongoClient mongoClient;
    private static MongoClientOptions.Builder builder;
    private static ServerAddress ServerAddress;

    @BeforeClass
    public static void setup() throws IOException {
        if (mongoClient == null) {

            builder = MongoClientOptions.builder();
            builder.connectionsPerHost(100);
            builder.maxWaitTime(60000);
            builder.connectTimeout(1500);
            builder.socketTimeout(60000);
            builder.socketKeepAlive(true);

            ServerAddress = new ServerAddress("localhost", 27017);
            mongoClient = new MongoClient(ServerAddress, builder.build());

        }
    }

    @Test
    public void givenMongoClient_whenConnectionCheck_thenCheckingForConnectionPoint() {

        String connectionPoint = mongoClient.getConnectPoint();
        assertNotNull(connectionPoint);
        assertFalse(connectionPoint.isEmpty());

    }
}

