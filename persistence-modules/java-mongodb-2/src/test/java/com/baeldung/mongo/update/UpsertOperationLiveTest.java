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

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.UpdateManyModel;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.WriteModel;
import com.mongodb.client.result.UpdateResult;

public class UpsertOperationLiveTest {

    private static MongoClient mongoClient;
    private static MongoDatabase db;
    private static MongoCollection<Document> collection;
    private static final String DATASET_JSON = "/vehicle.json";

    @BeforeClass
    public static void setup() throws IOException {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDatabase("baeldung");
            collection = db.getCollection("vehicle");
            collection.drop();

            InputStream is = BulkOperationLiveTest.class.getResourceAsStream(DATASET_JSON);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            reader.lines()
                .forEach(line -> collection.insertOne(Document.parse(line)));
            reader.close();
        }
    }

    @Test
    public void givenVehicleCollection_whenupdateOperations_thenCheckingForDocument() {
        UpdateOptions options = new UpdateOptions().upsert(true);
        UpdateResult updateResult = collection.updateOne(Filters.eq("modelName", "X5"), Updates.combine(Updates.set("companyName", "Hero Honda")), options);
        Document vehicleDetail = collection.find(Filters.eq("modelName", "X5"))
            .first();
        assertNotNull(vehicleDetail);
        assertFalse(vehicleDetail.isEmpty());

    }

    @Test
    public void givenVehicleCollection_whenupdateSetOnInsertOperations_thenCheckingForDocument() {

        UpdateOptions options = new UpdateOptions().upsert(true);
        UpdateResult updateSetOnInsertResult = collection.updateOne(Filters.eq("modelName", "GTPR"),
            Updates.combine(Updates.set("companyName", "Hero Honda"), Updates.setOnInsert("launchYear", 2022), Updates.setOnInsert("type", "Bike"), Updates.setOnInsert("registeredNo", "EPS 5562")), options);

        Document vehicleDetail = collection.find(Filters.eq("modelName", "GTPR"))
            .first();
        assertNotNull(vehicleDetail);
        assertFalse(vehicleDetail.isEmpty());
    }

    @Test
    public void givenVehicleCollection_whenfindOneAndUpdateOperations_thenCheckingForDocument() {

        FindOneAndUpdateOptions upsertOptions = new FindOneAndUpdateOptions();
        upsertOptions.returnDocument(ReturnDocument.AFTER);
        upsertOptions.upsert(true);

        Document resultDocument = collection.findOneAndUpdate(Filters.eq("modelName", "X7"), Updates.set("companyName", "Hero Honda1"), upsertOptions);
        Document vehicleDetail = collection.find(Filters.eq("modelName", "X7"))
            .first();
        assertNotNull(vehicleDetail);
        assertFalse(vehicleDetail.isEmpty());
    }

    @Test
    public void givenVehicleCollection_whenreplaceOneOperations_thenCheckingForDocument() {

        UpdateOptions options = new UpdateOptions().upsert(true);
        Document replaceDocument = new Document();
        replaceDocument.append("modelName", "GTPP")
            .append("companyName", "Hero Honda")
            .append("launchYear", 2022)
            .append("type", "Bike")
            .append("registeredNo", "EPS 5562");
        UpdateResult updateReplaceResult = collection.replaceOne(Filters.eq("modelName", "GTPP"), replaceDocument, options);

        Document vehicleDetail = collection.find(Filters.eq("modelName", "GTPP"))
            .first();
        assertNotNull(vehicleDetail);
        assertFalse(vehicleDetail.isEmpty());
    }

    @AfterClass
    public static void cleanUp() {
        mongoClient.close();
    }
}

