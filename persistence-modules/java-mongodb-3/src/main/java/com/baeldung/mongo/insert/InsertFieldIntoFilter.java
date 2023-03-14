package com.baeldung.mongo.insert;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.*;

public class InsertFieldIntoFilter {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static String collectionName;
    private static String databaseName;

    public static void setUp() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);

            databaseName = "baeldung";
            collectionName = "pet";

            database = mongoClient.getDatabase(databaseName);
            collection = database.getCollection(collectionName);
        }
    }

    public static void addFieldToExistingBsonFilter() {

        Bson existingFilter = and(eq("type", "Dog"), eq("gender", "Male"));

        Bson newFilter = and(existingFilter, gt("age", 5));
        FindIterable<Document> documents = collection.find(newFilter);

        MongoCursor<Document> cursor = documents.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }


    public static void addFieldToExistingBsonFilterUsingBsonDocument() {

        Bson existingFilter = eq("type", "Dog");
        BsonDocument existingBsonDocument = existingFilter.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry());

        Bson newFilter = gt("age", 5);
        BsonDocument newBsonDocument = newFilter.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry());

        existingBsonDocument.append("age", newBsonDocument.get("age"));

        FindIterable<Document> documents = collection.find(existingBsonDocument);

        MongoCursor<Document> cursor = documents.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public static void main(String args[]) {

        setUp();

        addFieldToExistingBsonFilter();

        addFieldToExistingBsonFilterUsingBsonDocument();
    }
}
