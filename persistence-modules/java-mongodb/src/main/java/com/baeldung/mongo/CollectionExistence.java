package com.baeldung.mongo;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class CollectionExistence {

    private static MongoClient mongoClient;

    private static String testCollectionName;
    private static String databaseName;

    public static void setUp() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);
        }
        databaseName = "baeldung";
        testCollectionName = "student";
    }

    public static void collectionExistsSolution() {

        DB db = mongoClient.getDB(databaseName);

        System.out.println("collectionName " + testCollectionName + db.collectionExists(testCollectionName));

    }

    public static void createCollectionSolution() {

        MongoDatabase database = mongoClient.getDatabase(databaseName);

        try {
            database.createCollection(testCollectionName);

        } catch (Exception exception) {
            System.err.println("Collection already Exists");
        }

    }

    public static void listCollectionNamesSolution() {

        MongoDatabase database = mongoClient.getDatabase(databaseName);
        boolean collectionExists = database.listCollectionNames()
            .into(new ArrayList<String>())
            .contains(testCollectionName);

        System.out.println("collectionExists:- " + collectionExists);

    }

    public static void countSolution() {

        MongoDatabase database = mongoClient.getDatabase(databaseName);

        MongoCollection<Document> collection = database.getCollection(testCollectionName);

        System.out.println(collection.count());

    }

    public static void main(String args[]) {

        //
        // Connect to cluster (default is localhost:27017)
        //
        setUp();

        //
        // Check the db existence using DB class's method
        //
        collectionExistsSolution();

        //
        // Check the db existence using the createCollection method of MongoDatabase class
        //
        createCollectionSolution();

        //
        // Check the db existence using the listCollectionNames method of MongoDatabase class
        //
        listCollectionNamesSolution();

        //
        // Check the db existence using the count method of MongoDatabase class
        //
        countSolution();

    }

}


