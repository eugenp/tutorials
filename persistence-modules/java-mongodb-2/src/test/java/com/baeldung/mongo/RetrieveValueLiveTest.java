package com.baeldung.mongo;

import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Projections.fields;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

public class RetrieveValueLiveTest {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static final String DATASET_JSON = "/travel.json";
    private static DB db;
    private static DBCollection dbCollection;

    @BeforeClass
    public static void setup() throws IOException {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);
            database = mongoClient.getDatabase("baeldung");
            db = mongoClient.getDB("baeldung");
            dbCollection = db.getCollection("travel");
            collection = database.getCollection("travel");
            collection.drop();

            InputStream is = BulkOperationLiveTest.class.getResourceAsStream(DATASET_JSON);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            reader.lines()
                .forEach(line -> collection.insertOne(Document.parse(line)));
            reader.close();
        }
    }

    @Test
    public void givenTravelCollection_whenfetchUsingFindOperations_thenCheckingForDocument() {

        BasicDBObject queryFilter = new BasicDBObject();

        BasicDBObject projection = new BasicDBObject();
        projection.put("passengerId", 1);
        projection.put("_id", 0);

        DBCursor dbCursor = dbCollection.find(queryFilter, projection);
        while (dbCursor.hasNext()) {
            System.out.println(dbCursor.next());
        }

        Document travelDetail = collection.find(Filters.eq("passengerId", 145))
            .first();
        assertNotNull(travelDetail);
        assertFalse(travelDetail.isEmpty());

    }

    @Test
    public void givenTravelCollection_whenfetchUsingAggregationOperations_thenCheckingForDocument() {

        ArrayList<Document> response = new ArrayList<>();
        ArrayList<Bson> pipeline = new ArrayList<>(Arrays.asList(project(fields(Projections.exclude("_id"), Projections.include("passengerId")))));
        collection.aggregate(pipeline)
            .allowDiskUse(true)
            .into(response);

        Document travelDetail = collection.find(Filters.eq("passengerId", 145))
            .first();
        assertNotNull(travelDetail);
        assertFalse(travelDetail.isEmpty());
    }

    @Test
    public void givenTravelCollection_whenfetchUsingAggregationUsingDocumentOperations_thenCheckingForDocument() {

        ArrayList<Document> response = new ArrayList<>();
        ArrayList<Document> pipeline = new ArrayList<>(Arrays.asList(new Document("$project", new Document("passengerId", 1L))));
        collection.aggregate(pipeline)
            .allowDiskUse(true)
            .into(response);

        Document travelDetail = collection.find(Filters.eq("passengerId", 145))
            .first();
        assertNotNull(travelDetail);
        assertFalse(travelDetail.isEmpty());
    }

    @AfterClass
    public static void cleanUp() {
        mongoClient.close();
    }
}

