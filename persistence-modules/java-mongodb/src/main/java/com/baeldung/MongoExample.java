package com.baeldung;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoExample {
    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {

            MongoDatabase database = mongoClient.getDatabase("myMongoDb");

            // print existing databases
            mongoClient.listDatabaseNames().forEach(System.out::println);

            boolean collectionExists = mongoClient.getDatabase("myMongoDb").listCollectionNames()
                    .into(new ArrayList<>()).contains("customers");
            if (!collectionExists) {
                database.createCollection("customers");
            }

            // print all collections in customers database
            database.listCollectionNames().forEach(System.out::println);

            // create data
            MongoCollection<Document> collection = database.getCollection("customers");
            Document document = new Document();
            document.put("name", "Shubham");
            document.put("company", "Baeldung");
            collection.insertOne(document);

            // update data
            Document query = new Document();
            query.put("name", "Shubham");
            Document newDocument = new Document();
            newDocument.put("name", "John");
            Document updateObject = new Document();
            updateObject.put("$set", newDocument);
            collection.updateOne(query, updateObject);

            // read data
            Document searchQuery = new Document();
            searchQuery.put("name", "John");
            FindIterable<Document> cursor = collection.find(searchQuery);
            try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
                while (cursorIterator.hasNext()) {
                    System.out.println(cursorIterator.next());
                }
            }

            // delete data
            Document deleteQuery = new Document();
            deleteQuery.put("name", "John");
            collection.deleteOne(deleteQuery);
        }
    }
}
