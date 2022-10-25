package com.baeldung.mongo.filter;

import com.baeldung.mongo.find.FindOperationLiveTest;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.mongodb.client.model.Filters.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FilterOperationLiveTest {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static final String DATASET_JSON = "/user.json";

    @BeforeClass
    public static void setUp() throws IOException {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);

            database = mongoClient.getDatabase("baeldung");
            collection = database.getCollection("user");

            collection.drop();

            InputStream is = FindOperationLiveTest.class.getResourceAsStream(DATASET_JSON);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            reader.lines()
                    .forEach(line -> collection.insertOne(Document.parse(line)));
            reader.close();
        }
    }

    @Test
    public void givenUserCollection_whenFetchingUsingEqualsOperator_thenCheckingForDocuments() {
        Bson filter = eq("userName", "Jack");
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingUsingNotEqualOperator_thenCheckingForDocuments() {
        Bson filter = ne("userName", "Jack");
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingUsingGreaterThanOperator_thenCheckingForDocuments() {
        Bson filter = gt("age", 25);
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingUsingLessThanOperator_thenCheckingForDocuments() {
        Bson filter = lt("age", 25);
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingUsingInOperator_thenCheckingForDocuments() {
        Bson filter = in("userName", "Jack", "Lisa");
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingUsingNotInOperator_thenCheckingForDocuments() {
        Bson filter = nin("userName", "Jack", "Lisa");
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingUsingAndOperator_thenCheckingForDocuments() {
        Bson filter = and(gt("age", 25), eq("role", "Admin"));
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingUsingOrOperator_thenCheckingForDocuments() {
        Bson filter = or(gt("age", 30), eq("role", "Admin"));
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingExistsOperator_thenCheckingForDocuments() {
        Bson filter = exists("type");
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingUsingRegexOperator_thenCheckingForDocuments() {
        Bson filter = regex("userName", "a");
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @AfterClass
    public static void cleanUp() {
        mongoClient.close();
    }
}
