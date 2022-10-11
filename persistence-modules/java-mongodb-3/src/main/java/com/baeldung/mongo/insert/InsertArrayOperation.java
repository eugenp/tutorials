package com.baeldung.mongo.insert;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

public class InsertArrayOperation {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static DB db;
    private static DBCollection dbCollection;
    private static String collectionName;
    private static String databaseName;

    public static void setUp() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);

            databaseName = "baeldung";
            collectionName = "student";

            database = mongoClient.getDatabase(databaseName);
            collection = database.getCollection(collectionName);

            db = mongoClient.getDB(databaseName);
            dbCollection = db.getCollection(collectionName);
        }
    }

    public static void insertSingleBsonDocumentWithStringArray() {
        BasicDBList coursesList = new BasicDBList();
        coursesList.add("Chemistry");
        coursesList.add("Science");

        DBObject student = new BasicDBObject().append("studentId", "STUD1")
            .append("name", "Jim")
            .append("age", 13)
            .append("courses", coursesList);

        dbCollection.insert(student);

        Bson filter = eq("studentId", "STUD1");
        FindIterable<Document> documents = collection.find(filter);

        MongoCursor<Document> cursor = documents.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public static void insertSingleDocumentWithStringArray() {
        List<String> coursesList = new ArrayList<>();
        coursesList.add("Science");
        coursesList.add("Geography");

        Document student = new Document().append("studentId", "STUD2")
            .append("name", "Sam")
            .append("age", 13)
            .append("courses", coursesList);

        collection.insertOne(student);

        Bson filter = eq("studentId", "STUD2");
        FindIterable<Document> documents = collection.find(filter);

        MongoCursor<Document> cursor = documents.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public static void insertMultipleDocumentsWithStringArray() {
        List<String> coursesList1 = new ArrayList<>();
        coursesList1.add("Chemistry");
        coursesList1.add("Geography");

        Document student1 = new Document().append("studentId", "STUD3")
            .append("name", "Sarah")
            .append("age", 12)
            .append("courses", coursesList1);

        List<String> coursesList2 = new ArrayList<>();
        coursesList2.add("Maths");
        coursesList2.add("History");

        Document student2 = new Document().append("studentId", "STUD4")
            .append("name", "Tom")
            .append("age", 13)
            .append("courses", coursesList2);

        List<Document> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        collection.insertMany(students);

        Bson filter = in("studentId", "STUD3", "STUD4");
        FindIterable<Document> documents = collection.find(filter);

        MongoCursor<Document> cursor = documents.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public static void insertSingleDocumentWithObjectArray() {
        Document course1 = new Document().append("name", "C1")
            .append("points", 5);

        Document course2 = new Document().append("name", "C2")
            .append("points", 7);

        List<Document> coursesList = new ArrayList<>();
        coursesList.add(course1);
        coursesList.add(course2);

        Document student = new Document().append("studentId", "STUD5")
            .append("name", "Avin")
            .append("age", 13)
            .append("courses", coursesList);

        collection.insertOne(student);

        Bson filter = eq("studentId", "STUD5");
        FindIterable<Document> documents = collection.find(filter);

        MongoCursor<Document> cursor = documents.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public static void main(String args[]) {

        setUp();

        insertSingleBsonDocumentWithStringArray();

        insertSingleDocumentWithStringArray();

        insertMultipleDocumentsWithStringArray();

        insertSingleDocumentWithObjectArray();
    }
}
