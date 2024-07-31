package com.baeldung.mongo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.baeldung.ConnectionCheck;
import com.mongodb.MongoClient;

public class ConnectionCheckLiveTest {

    ConnectionCheck ConnectionCheck = new ConnectionCheck();

    @Test
    public void givenMongoClient_whenConnectionCheck_thenCheckingForConnectionPoint() {

        MongoClient mongoClient = ConnectionCheck.checkingConnection();
        String connectionPoint = mongoClient.getConnectPoint();
        assertNotNull(connectionPoint);
        assertFalse(connectionPoint.isEmpty());

    }
}