package com.baeldung.mongo.update;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

public class UpdateFields {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;

    public static void updateOne() {

        UpdateResult updateResult = collection.updateOne(Filters.eq("student_name", "Paul Starc"), Updates.set("address", "Hostel 2"));

        System.out.println("updateResult:- " + updateResult);
    }

    public static void updateMany() {

        UpdateResult updateResult = collection.updateMany(Filters.lt("age", 20), Updates.set("Review", true));

        System.out.println("updateResult:- " + updateResult);

    }

    public static void replaceOne() {

        Document replaceDocument = new Document();
        replaceDocument.append("student_id", 8764)
            .append("student_name", "Paul Starc")
            .append("address", "Hostel 3")
            .append("age", 18)
            .append("roll_no", 199406);

        UpdateResult updateResult = collection.replaceOne(Filters.eq("student_id", 8764), replaceDocument);

        System.out.println("updateResult:- " + updateResult);

    }

    public static void findOneAndReplace() {

        Document replaceDocument = new Document();
        replaceDocument.append("student_id", 8764)
            .append("student_name", "Paul Starc")
            .append("address", "Hostel 4")
            .append("age", 18)
            .append("roll_no", 199406);
        Document sort = new Document("roll_no", 1);
        Document projection = new Document("_id", 0).append("student_id", 1)
            .append("address", 1);
        Document resultDocument = collection.findOneAndReplace(Filters.eq("student_id", 8764), replaceDocument, new FindOneAndReplaceOptions().upsert(true)
            .sort(sort)
            .projection(projection)
            .returnDocument(ReturnDocument.AFTER));

        System.out.println("resultDocument:- " + resultDocument);

    }

    public static void findOneAndUpdate() {

        Document sort = new Document("roll_no", 1);
        Document projection = new Document("_id", 0).append("student_id", 1)
            .append("address", 1);
        Document resultDocument = collection.findOneAndUpdate(Filters.eq("student_id", 8764), Updates.inc("roll_no", 5), new FindOneAndUpdateOptions().upsert(true)
            .sort(sort)
            .projection(projection)
            .returnDocument(ReturnDocument.AFTER));

        System.out.println("resultDocument:- " + resultDocument);
    }

    public static void setup() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);
            database = mongoClient.getDatabase("baeldung");
            collection = database.getCollection("student");

        }
    }

    public static void main(String[] args) {

        //
        // Connect to cluster (default is localhost:27017)
        //
        setup();

        //
        // Update a document using updateOne method
        //
        updateOne();

        //
        // Update documents using updateMany method
        //
        updateMany();

        //
        // replace a document using replaceOne method
        //
        replaceOne();

        //
        // replace a document using findOneAndReplace method
        //
        findOneAndReplace();

        //
        // Update a document using findOneAndUpdate method
        //
        findOneAndUpdate();

    }

}

