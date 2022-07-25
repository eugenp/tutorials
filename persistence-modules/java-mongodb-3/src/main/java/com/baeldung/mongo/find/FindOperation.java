package com.baeldung.mongo.find;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

public class FindOperation {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static String collectionName;
    private static String databaseName;

    public static void setUp() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);

            databaseName = "baeldung";
            collectionName = "employee";

            database = mongoClient.getDatabase(databaseName);
            collection = database.getCollection(collectionName);
        }
    }

    public static void retrieveAllDocumentsUsingFind() {
        FindIterable<Document> documents = collection.find();

        MongoCursor<Document> cursor = documents.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public static void retrieveAllDocumentsUsingFindWithQueryFilter() {
        Bson filter = eq("department", "Engineering");
        FindIterable<Document> documents = collection.find(filter);

        MongoCursor<Document> cursor = documents.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public static void retrieveAllDocumentsUsingFindWithQueryFilterAndProjection() {
        Bson filter = eq("department", "Engineering");
        Bson projection = fields(include("name", "age"));
        FindIterable<Document> documents = collection.find(filter)
          .projection(projection);

        MongoCursor<Document> cursor = documents.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public static void retrieveFirstDocument() {
        FindIterable<Document> documents = collection.find();
        Document document = documents.first();

        System.out.println(document);
    }

    public static void main(String args[]) {

        setUp();

        retrieveAllDocumentsUsingFind();

        retrieveAllDocumentsUsingFindWithQueryFilter();

        retrieveAllDocumentsUsingFindWithQueryFilterAndProjection();

        retrieveFirstDocument();
    }
}

