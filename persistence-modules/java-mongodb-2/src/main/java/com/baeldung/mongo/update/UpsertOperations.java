package com.baeldung.mongo;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

public class UpsertOperations {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static String testCollectionName;
    private static String databaseName;

    public static void setUp() {

        databaseName = "baeldung";
        testCollectionName = "vehicle";
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);
            database = mongoClient.getDatabase(databaseName);
            collection = database.getCollection(testCollectionName);

        }

    }

    public static void updateOperations() {
        UpdateOptions options = new UpdateOptions().upsert(true);
        UpdateResult updateResult = collection.updateOne(Filters.eq("modelName", "X5"), Updates.combine(Updates.set("companyName", "Hero Honda")), options);
        System.out.println("updateResult:- " + updateResult);

    }

    public static void updateSetOnInsertOperations() {

        UpdateOptions options = new UpdateOptions().upsert(true);
        UpdateResult updateSetOnInsertResult = collection.updateOne(Filters.eq("modelName", "GTPR"),
            Updates.combine(Updates.set("companyName", "Hero Honda"), Updates.setOnInsert("launchYear", 2022), Updates.setOnInsert("type", "Bike"), Updates.setOnInsert("registeredNo", "EPS 5562")), options);

        System.out.println("updateSetOnInsertResult:- " + updateSetOnInsertResult);

    }

    public static void findOneAndUpdateOperations() {

        FindOneAndUpdateOptions upsertOptions = new FindOneAndUpdateOptions();
        upsertOptions.returnDocument(ReturnDocument.AFTER);
        upsertOptions.upsert(true);

        Document resultDocument = collection.findOneAndUpdate(Filters.eq("modelName", "X7"), Updates.set("companyName", "Hero Honda"), upsertOptions);
        System.out.println("resultDocument:- " + resultDocument);

    }

    public static void replaceOneOperations() {

        UpdateOptions options = new UpdateOptions().upsert(true);
        Document replaceDocument = new Document();
        replaceDocument.append("modelName", "GTPP")
            .append("companyName", "Hero Honda")
            .append("launchYear", 2022)
            .append("type", "Bike")
            .append("registeredNo", "EPS 5562");
        UpdateResult updateReplaceResult = collection.replaceOne(Filters.eq("modelName", "GTPP"), replaceDocument, options);

        System.out.println("updateReplaceResult:- " + updateReplaceResult);
    }

    public static void main(String args[]) {

        //
        // Connect to cluster (default is localhost:27017)
        //
        setUp();

        //
        // Update with upsert operation
        //

        updateOperations();

        //
        // Update with upsert operation using setOnInsert
        //

        updateSetOnInsertOperations();

        //
        // Update with upsert operation using findOneAndUpdate
        //

        findOneAndUpdateOperations();

        //
        // Update with upsert operation using replaceOneOperations
        //

        replaceOneOperations();

    }
}

