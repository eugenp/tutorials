package com.baeldung.mongo.update;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

public class MultipleFieldsExample {

    public static void main(String[] args) {

        //
        // Connect to cluster (default is localhost:27017)
        //

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("baeldung");
        MongoCollection<Document> collection = database.getCollection("employee");

        //
        // Filter on the basis of employee_id
        //

        BasicDBObject searchQuery = new BasicDBObject("employee_id", 794875);

        //
        // Update the fields in Document
        //

        BasicDBObject updateFields = new BasicDBObject();
        updateFields.append("department_id", 3);
        updateFields.append("job", "Sales Manager");
        BasicDBObject setQuery = new BasicDBObject();
        setQuery.append("$set", updateFields);
        UpdateResult updateResult = collection.updateMany(searchQuery, setQuery);

        System.out.println("updateResult:- " + updateResult);
        System.out.println("updateResult:- " + updateResult.getModifiedCount());

    }
}
