package com.baeldung.mongo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.UpdateManyModel;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.WriteModel;
import com.mongodb.client.result.UpdateResult;

public class BulkOperationLiveTest {

    private static MongoClient mongoClient;
    private static MongoDatabase db;
    private static MongoCollection<Document> collection;
    private static final String DATASET_JSON = "/populations.json";

    @BeforeClass
    public static void setup() throws IOException {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDatabase("baeldung");
            collection = db.getCollection("populations");
            collection.drop();

            InputStream is = BulkOperationLiveTest.class.getResourceAsStream(DATASET_JSON);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            reader.lines()
                .forEach(line -> collection.insertOne(Document.parse(line)));
            reader.close();
        }
    }

    @Test
    public void givenPopulationCollection_whenBulkOperations_thenCheckingForDocument() {

        List<WriteModel<Document>> writeOperations = new ArrayList<WriteModel<Document>>();
        writeOperations.add(new InsertOneModel<Document>(new Document("cityId", 1128).append("cityName", "Kathmandu")
            .append("countryName", "Nepal")
            .append("continentName", "Asia")
            .append("population", 12)));
        writeOperations.add(new InsertOneModel<Document>(new Document("cityId", 1130).append("cityName", "Mumbai")
            .append("countryName", "India")
            .append("continentName", "Asia")
            .append("population", 55)));
        writeOperations.add(new UpdateOneModel<Document>(new Document("cityName", "New Delhi"), // filter to update document
            new Document("$set", new Document("status", "High Population")) // update
        ));
        writeOperations.add(new UpdateManyModel<Document>(new Document("cityName", "London"), // filter to update multiple documents
            new Document("$set", new Document("status", "Low Population")) // update
        ));
        writeOperations.add(new DeleteOneModel<Document>(new Document("cityName", "Mexico City")));
        writeOperations.add(new ReplaceOneModel<Document>(new Document("cityName", "New York"), new Document("cityId", 1124).append("cityName", "New York")
            .append("countryName", "United States")
            .append("continentName", "North America")
            .append("population", 28)));

        Document orderDetail = collection.find(Filters.eq("cityId", 1124))
            .first();
        assertNotNull(orderDetail);
        assertFalse(orderDetail.isEmpty());

    }

    @AfterClass
    public static void cleanUp() {
        mongoClient.close();
    }

}

