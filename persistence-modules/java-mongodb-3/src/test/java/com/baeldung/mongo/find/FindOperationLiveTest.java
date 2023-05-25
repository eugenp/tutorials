package com.baeldung.mongo.find;

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

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static org.junit.Assert.*;

public class FindOperationLiveTest {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static final String DATASET_JSON = "/employee.json";

    @BeforeClass
    public static void setUp() throws IOException {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);

            database = mongoClient.getDatabase("baeldung");
            collection = database.getCollection("employee");

            collection.drop();

            InputStream is = FindOperationLiveTest.class.getResourceAsStream(DATASET_JSON);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            reader.lines()
              .forEach(line -> collection.insertOne(Document.parse(line)));
            reader.close();
        }
    }

    @Test
    public void givenEmployeeCollection_whenFetchingUsingFindOperations_thenCheckingForDocuments() {
        FindIterable<Document> documents = collection.find();
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenEmployeeCollection_whenFetchingUsingFindOperationsWithFilters_thenCheckingForDocuments() {
        Bson filter = eq("department", "Engineering");
        FindIterable<Document> documents = collection.find(filter);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenEmployeeCollection_whenFetchingUsingFindOperationsWithFiltersAndProjection_thenCheckingForDocuments() {
        Bson filter = eq("department", "Engineering");
        Bson projection = fields(include("name", "age"));
        FindIterable<Document> documents = collection.find(filter)
          .projection(projection);
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenEmployeeCollection_whenFetchingFirstDocumentUsingFindOperations_thenCheckingForDocument() {
        Document employee = collection.find()
          .first();

        assertNotNull(employee);
        assertFalse(employee.isEmpty());
    }

    @AfterClass
    public static void cleanUp() {
        mongoClient.close();
    }
}

