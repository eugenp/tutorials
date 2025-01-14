package com.baeldung.mongo.filter;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.ne;
import static com.mongodb.client.model.Filters.nin;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Filters.regex;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

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

            InputStream is = FilterOperationLiveTest.class.getResourceAsStream(DATASET_JSON);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            reader.lines()
                    .forEach(line -> collection.insertOne(Document.parse(line)));
            reader.close();
        }
    }

    @Test
    public void givenUserCollection_whenFetchingUsingEqualsOperator_thenFindMatchingDocuments() {
        Bson filter = eq("userName", "Jack");
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingUsingNotEqualOperator_thenFindMatchingDocuments() {
        Bson filter = ne("userName", "Jack");
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingUsingGreaterThanOperator_thenFindMatchingDocuments() {
        Bson filter = gt("age", 25);
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingUsingLessThanOperator_thenFindMatchingDocuments() {
        Bson filter = lt("age", 25);
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingUsingInOperator_thenFindMatchingDocuments() {
        Bson filter = in("userName", "Jack", "Lisa");
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingUsingNotInOperator_thenFindMatchingDocuments() {
        Bson filter = nin("userName", "Jack", "Lisa");
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingUsingAndOperator_thenFindMatchingDocuments() {
        Bson filter = and(gt("age", 25), eq("role", "Admin"));
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingUsingOrOperator_thenFindMatchingDocuments() {
        Bson filter = or(gt("age", 30), eq("role", "Admin"));
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingExistsOperator_thenFindMatchingDocuments() {
        Bson filter = exists("type");
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenUserCollection_whenFetchingUsingRegexOperator_thenFindMatchingDocuments() {
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
