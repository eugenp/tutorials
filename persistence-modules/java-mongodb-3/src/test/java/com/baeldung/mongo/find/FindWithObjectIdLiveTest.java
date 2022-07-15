package com.baeldung.mongo.find;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.Assert.*;

public class FindWithObjectIdLiveTest {

    private static final String OBJECT_ID_FIELD = "_id";

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static final String DATASET_JSON = "/vehicle.json";

    @BeforeClass
    public static void setUp() throws IOException {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);

            database = mongoClient.getDatabase("baeldung");
            collection = database.getCollection("vehicle");

            collection.drop();

            InputStream is = FindOperationLiveTest.class.getResourceAsStream(DATASET_JSON);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            reader.lines()
              .forEach(line -> collection.insertOne(Document.parse(line)));
            reader.close();
        }
    }

    @Test
    public void givenVehicleCollection_whenFetchingDocumentsUsingObjectId_thenCheckingForDocuments() {
        Document vehicle = collection.find()
          .first();
        ObjectId objectId = (ObjectId) vehicle.get(OBJECT_ID_FIELD);

        FindIterable<Document> documents = collection.find(eq(OBJECT_ID_FIELD, objectId));
        MongoCursor<Document> cursor = documents.iterator();

        assertNotNull(cursor);
        assertTrue(cursor.hasNext());
    }

    @Test
    public void givenVehicleCollection_whenFetchingFirstDocumentUsingObjectId_thenCheckingForDocument() {
        Document vehicle = collection.find()
          .first();
        ObjectId objectId = (ObjectId) vehicle.get(OBJECT_ID_FIELD);

        Document queriedVehicle = collection.find(eq(OBJECT_ID_FIELD, objectId))
          .first();

        assertNotNull(queriedVehicle);
        assertEquals(vehicle.get(OBJECT_ID_FIELD), queriedVehicle.get(OBJECT_ID_FIELD));
    }

    @Test
    public void givenVehicleCollection_whenFetchingUsingRandomObjectId_thenCheckingForDocument() {
        Document vehicle = collection.find(eq(OBJECT_ID_FIELD, new ObjectId()))
          .first();

        assertNull(vehicle);
    }

    @AfterClass
    public static void cleanUp() {
        mongoClient.close();
    }
}
