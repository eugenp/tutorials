package com.baeldung.mongo.connectioncheck;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.mongodb.ServerAddress;
import com.mongodb.connection.ServerDescription;
import com.mongodb.client.MongoClient;

public class ConnectionCheckLiveTest {

    ConnectionCheck ConnectionCheck = new ConnectionCheck();

    @Test
    public void givenMongoClient_whenConnectionCheck_thenCheckingForConnectionPoint() {

        MongoClient mongoClient = ConnectionCheck.checkingConnection();

        ServerAddress serverAddress = mongoClient.getClusterDescription()
            .getServerDescriptions()
            .stream()
            .findFirst()
            .map(ServerDescription::getAddress)
            .orElse(null);

        String connectionPoint = serverAddress.toString();
        assertNotNull(connectionPoint);
        assertFalse(connectionPoint.isEmpty());

    }
}