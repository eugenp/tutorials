package com.baeldung.mongo.update;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

public class PustSetOperation {

    private static MongoClient mongoClient;

    private static String testCollectionName;
    private static String databaseName;

    public static void setUp() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);
        }
        databaseName = "baeldung";
        testCollectionName = "marks";
    }

    public static void pushSetSolution() {

        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(testCollectionName);

        Document subjectData = new Document().append("subjectId", 126)
            .append("subjectName", "Java Programming")
            .append("marks", 70);
        UpdateResult updateQueryResult = collection.updateOne(Filters.eq("studentId", 1023), Updates.combine(Updates.set("totalMarks", 170), Updates.push("subjectDetails", subjectData)));
        System.out.println("updateQueryResult:- " + updateQueryResult);

    }

    public static void main(String args[]) {

        //
        // Connect to cluster (default is localhost:27017)
        //
        setUp();

        //
        // Push document into the array and set a field
        //
        pushSetSolution();

    }
}

