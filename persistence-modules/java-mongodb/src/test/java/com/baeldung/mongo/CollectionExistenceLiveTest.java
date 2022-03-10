package com.baeldung.mongo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class CollectionExistenceLiveTest {

    private MongoClient mongoClient;
    private String testCollectionName;
    private String databaseName;

    @Before
    public void setup() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);
            databaseName = "baeldung";
            testCollectionName = "student";

            // Create a new collection if it doesn't exists.
            try {
                MongoDatabase database = mongoClient.getDatabase(databaseName);
                database.createCollection(testCollectionName);

            } catch (Exception exception) {

                System.out.println("Collection already Exists");
            }

        }
    }

    @Test
    public void givenCreateCollection_whenCollectionAlreadyExists_thenCheckingForCollectionExistence() {

        MongoDatabase database = mongoClient.getDatabase(databaseName);
        Boolean collectionStatus = false;
        Boolean expectedStatus = true;

        try {
            database.createCollection(testCollectionName);

        } catch (Exception exception) {
            collectionStatus = true;
            System.err.println("Collection already Exists");
        }

        assertEquals(expectedStatus, collectionStatus);

    }

    @Test
    public void givenCollectionExists_whenCollectionAlreadyExists_thenCheckingForCollectionExistence() {

        DB db = mongoClient.getDB(databaseName);
        Boolean collectionStatus = db.collectionExists(testCollectionName);

        Boolean expectedStatus = true;
        assertEquals(expectedStatus, collectionStatus);

    }

    @Test
    public void givenListCollectionNames_whenCollectionAlreadyExists_thenCheckingForCollectionExistence() {

        MongoDatabase database = mongoClient.getDatabase(databaseName);
        boolean collectionExists = database.listCollectionNames()
            .into(new ArrayList<String>())
            .contains(testCollectionName);

        Boolean expectedStatus = true;
        assertEquals(expectedStatus, collectionExists);

    }

    @Test
    public void givenCount_whenCollectionAlreadyExists_thenCheckingForCollectionExistence() {

        MongoDatabase database = mongoClient.getDatabase(databaseName);

        MongoCollection<Document> collection = database.getCollection(testCollectionName);
        Boolean collectionExists = collection.count() > 0 ? true : false;

        Boolean expectedStatus = false;
        assertEquals(expectedStatus, collectionExists);

    }
}

