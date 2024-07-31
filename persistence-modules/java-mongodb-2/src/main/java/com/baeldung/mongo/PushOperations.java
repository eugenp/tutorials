package com.baeldung.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

public class PushOperations {

    private static MongoClient mongoClient;
    private static String testCollectionName;
    private static String databaseName;

    public static void setUp() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);
        }

        databaseName = "baeldung";
        testCollectionName = "orders";

    }

    public static void pushOperationUsingDBObject() {

        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(testCollectionName);
        DBObject listItem = new BasicDBObject("items", new BasicDBObject("itemName", "PIZZA MANIA").append("quantity", 1)
            .append("price", 800));
        BasicDBObject searchFilter = new BasicDBObject("customerId", 1023);
        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.append("$push", listItem);
        UpdateResult updateResult = collection.updateOne(searchFilter, updateQuery);

        System.out.println("updateResult:- " + updateResult);
    }

    public static void pushOperationUsingDocument() {

        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(testCollectionName);

        Document item = new Document().append("itemName", "PIZZA MANIA")
            .append("quantity", 1)
            .append("price", 800);
        UpdateResult updateResult = collection.updateOne(Filters.eq("customerId", 1023), Updates.push("items", item));

        System.out.println("updateResult:- " + updateResult);
    }

    public static void addToSetOperation() {

        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(testCollectionName);

        Document item = new Document().append("itemName", "PIZZA MANIA")
            .append("quantity", 1)
            .append("price", 800);
        UpdateResult updateResult = collection.updateOne(Filters.eq("customerId", 1023), Updates.addToSet("items", item));
        System.out.println("updateResult:- " + updateResult);
    }

    public static void main(String args[]) {

        //
        // Connect to cluster (default is localhost:27017)
        //
        setUp();

        //
        // Push document into the array using DBObject
        //

        pushOperationUsingDBObject();

        //
        // Push document into the array using Document.
        //

        pushOperationUsingDocument();

        //
        // Push document into the array using addToSet operator.
        //
        addToSetOperation();

    }

}

