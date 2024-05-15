package com.baeldung.mongo.insert;

import com.baeldung.mongo.find.FindOperationLiveTest;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.mongodb.client.model.Filters.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class InsertFieldIntoFilterLiveTest {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static final String DATASET_JSON = "/pet.json";

    @BeforeClass
    public static void setUp() throws IOException {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);

            database = mongoClient.getDatabase("baeldung");
            collection = database.getCollection("pet");

            collection.drop();

            InputStream is = FindOperationLiveTest.class.getResourceAsStream(DATASET_JSON);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            reader.lines()
                    .forEach(line -> collection.insertOne(Document.parse(line)));
            reader.close();
        }
    }

    @Test
    public void givenPetCollection_whenFetchingAfterAddingFieldToFilter_thenFindMatchingDocuments() {
        Bson existingFilter = and(eq("type", "Dog"), eq("gender", "Male"));

        Bson newFilter = and(existingFilter, gt("age", 5));
        MongoCursor<Document> cursor = collection.find(newFilter).iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenPetCollection_whenFetchingAfterAddingFieldToFilterUsingBsonDocument_thenFindMatchingDocuments() {
        Bson existingFilter = eq("type", "Dog");
        BsonDocument existingBsonDocument = existingFilter.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry());

        Bson newFilter = gt("age", 5);
        BsonDocument newBsonDocument = newFilter.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry());

        existingBsonDocument.append("age", newBsonDocument.get("age"));
        MongoCursor<Document> cursor = collection.find(existingBsonDocument).iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }
}
