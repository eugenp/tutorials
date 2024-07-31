package com.baeldung.mongo;

import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Projections.fields;

import java.util.ArrayList;
import java.util.Arrays;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;

public class RetrieveValue {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static String testCollectionName;
    private static String databaseName;

    public static void setUp() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);
        }

        databaseName = "baeldung";
        testCollectionName = "travel";

    }

    public static void retrieveValueUsingFind() {

        DB database = mongoClient.getDB(databaseName);
        DBCollection collection = database.getCollection(testCollectionName);

        BasicDBObject queryFilter = new BasicDBObject();

        BasicDBObject projection = new BasicDBObject();
        projection.put("passengerId", 1);
        projection.put("_id", 0);

        DBCursor dbCursor = collection.find(queryFilter, projection);
        while (dbCursor.hasNext()) {
            System.out.println(dbCursor.next());
        }

    }

    public static void retrieveValueUsingAggregation() {

        ArrayList<Document> response = new ArrayList<>();

        ArrayList<Bson> pipeline = new ArrayList<>(Arrays.asList(

            project(fields(Projections.exclude("_id"), Projections.include("passengerId")))));

        database = mongoClient.getDatabase(databaseName);

        database.getCollection(testCollectionName)
            .aggregate(pipeline)
            .allowDiskUse(true)
            .into(response);

        System.out.println("response:- " + response);

    }

    public static void retrieveValueAggregationUsingDocument() {

        ArrayList<Document> response = new ArrayList<>();

        ArrayList<Document> pipeline = new ArrayList<>(Arrays.asList(new Document("$project", new Document("passengerId", 1L))));

        database = mongoClient.getDatabase(databaseName);

        database.getCollection(testCollectionName)
            .aggregate(pipeline)
            .allowDiskUse(true)
            .into(response);

        System.out.println("response:- " + response);

    }

    public static void main(String args[]) {

        //
        // Connect to cluster (default is localhost:27017)
        //
        setUp();

        //
        // Fetch the data using find query with projected fields
        //

        retrieveValueUsingFind();

        //
        // Fetch the data using aggregate pipeline query with projected fields
        //

        retrieveValueUsingAggregation();

        //
        // Fetch the data using aggregate pipeline document query with projected fields
        //

        retrieveValueAggregationUsingDocument();

    }
}

