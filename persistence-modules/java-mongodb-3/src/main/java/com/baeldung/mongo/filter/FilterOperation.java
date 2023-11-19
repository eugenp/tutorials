package com.baeldung.mongo.filter;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.*;

public class FilterOperation {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static String collectionName;
    private static String databaseName;

    public static void setUp() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);

            databaseName = "baeldung";
            collectionName = "user";

            database = mongoClient.getDatabase(databaseName);
            collection = database.getCollection(collectionName);
        }
    }

    public static void equalsOperator() {
        Bson filter = eq("userName", "Jack");
        FindIterable<Document> documents = collection.find(filter);

        printResult(documents);
    }

    public static void notEqualOperator() {
        Bson filter = ne("userName", "Jack");
        FindIterable<Document> documents = collection.find(filter);

        printResult(documents);
    }

    public static void greaterThanOperator() {
        Bson filter = gt("age", 25);
        FindIterable<Document> documents = collection.find(filter);

        printResult(documents);
    }

    public static void lessThanOperator() {
        Bson filter = lt("age", 25);
        FindIterable<Document> documents = collection.find(filter);

        printResult(documents);
    }

    public static void inOperator() {
        Bson filter = in("userName", "Jack", "Lisa");
        FindIterable<Document> documents = collection.find(filter);

        printResult(documents);
    }

    public static void notInOperator() {
        Bson filter = nin("userName", "Jack", "Lisa");
        FindIterable<Document> documents = collection.find(filter);

        printResult(documents);
    }

    public static void andOperator() {
        Bson filter = and(gt("age", 25), eq("role", "Admin"));
        FindIterable<Document> documents = collection.find(filter);

        printResult(documents);
    }

    public static void orOperator() {
        Bson filter = or(gt("age", 30), eq("role", "Admin"));
        FindIterable<Document> documents = collection.find(filter);

        printResult(documents);
    }

    public static void existsOperator() {
        Bson filter = exists("type");
        FindIterable<Document> documents = collection.find(filter);

        printResult(documents);
    }

    public static void regexOperator() {
        Bson filter = regex("userName", "a");
        FindIterable<Document> documents = collection.find(filter);

        printResult(documents);
    }

    private static void printResult(FindIterable<Document> documents) {
        MongoCursor<Document> cursor = documents.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public static void main(String args[]) {

        setUp();

        equalsOperator();

        notEqualOperator();

        greaterThanOperator();

        lessThanOperator();

        inOperator();

        notInOperator();

        andOperator();

        orOperator();

        existsOperator();

        regexOperator();
    }
}
