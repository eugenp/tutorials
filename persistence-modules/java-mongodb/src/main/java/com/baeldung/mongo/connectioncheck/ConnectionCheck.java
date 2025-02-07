package com.baeldung.mongo.connectioncheck;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.bson.Document;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConnectionCheck {

    public static MongoClient checkingConnection() {

        MongoClientSettings settings = MongoClientSettings.builder()
            .applyToClusterSettings(builder ->
                builder.hosts(Collections.singletonList(new ServerAddress("localhost", 27017))))
            .applyToConnectionPoolSettings(builder -> {
                builder.maxSize(100);
                builder.maxWaitTime(60000, TimeUnit.MILLISECONDS);
            })
            .applyToSocketSettings(builder -> {
                builder.connectTimeout(1500,TimeUnit.MILLISECONDS);
                builder.readTimeout(60000,TimeUnit.MILLISECONDS);
            })
            .build();

        MongoClient mongoClient = MongoClients.create(settings);

        try {
            System.out.println("MongoDB Server is Up");

            MongoDatabase database = mongoClient.getDatabase("baeldung");

            Document stats = database.runCommand(new Document("dbStats", 1));
            System.out.println(stats.toJson());

        } catch (Exception e) {
            System.out.println("MongoDB Server is Down");
            e.printStackTrace();
        }

        return mongoClient;

    }

    public static void main(String[] args) {
        //
        // Connection check
        //
        checkingConnection();
    }

}