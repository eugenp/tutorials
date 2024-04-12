package com.baeldung;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

public class ConnectionCheck {

    public static MongoClient checkingConnection() {

        MongoClientOptions.Builder builder = MongoClientOptions.builder();

        builder.connectionsPerHost(100);
        builder.maxWaitTime(60000);
        builder.connectTimeout(1500);
        builder.socketTimeout(60000);
        builder.socketKeepAlive(true);

        ServerAddress ServerAddress = new ServerAddress("localhost", 27017);
        MongoClient mongoClient = new MongoClient(ServerAddress, builder.build());

        try {
            System.out.println("MongoDB Server is Up:- " + mongoClient.getAddress());
            DB db = mongoClient.getDB("baeldung");
            System.out.println(mongoClient.getConnectPoint());
            System.out.println(db.getStats());
        } catch (Exception e) {
            System.out.println("MongoDB Server is Down");
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