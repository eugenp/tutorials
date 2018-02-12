package com.baeldung.spring.data.dynamodb.repository.rule;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.junit.rules.ExternalResource;
import org.springframework.beans.factory.annotation.Autowired;

public class LocalDynamoDBCreationRule extends ExternalResource {

    protected DynamoDBProxyServer server;
    protected AmazonDynamoDB amazonDynamoDB;

    @Override
    protected void before() throws Exception {
        System.setProperty("sqlite4java.library.path", "native-libs");
        String port = "8000";
        this.server = ServerRunner.createServerFromCommandLineArgs(new String[]{"-inMemory", "-port", port});
        server.start();
    }

    @Override
    protected void after() {
        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public void setAmazonDynamoDB(AmazonDynamoDB amazonDynamoDB) {
        this.amazonDynamoDB = amazonDynamoDB;
    }

}
