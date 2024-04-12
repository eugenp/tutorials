package com.baeldung;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.UpdateManyModel;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.WriteModel;

public class BulkOperations {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static String testCollectionName;
    private static String databaseName;

    public static void setUp() {

        databaseName = "baeldung";
        testCollectionName = "populations";
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);
            database = mongoClient.getDatabase(databaseName);
            collection = database.getCollection(testCollectionName);

        }

    }

    public static void bulkOperations() {

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

        BulkWriteResult bulkWriteResult = collection.bulkWrite(writeOperations);
        System.out.println("bulkWriteResult:- " + bulkWriteResult);
    }

    public static void main(String args[]) {

        //
        // Connect to cluster (default is localhost:27017)
        //
        setUp();

        //
        // Bulk operations
        //

        bulkOperations();

    }
}

