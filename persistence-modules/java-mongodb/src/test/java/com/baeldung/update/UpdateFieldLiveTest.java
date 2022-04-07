package com.baeldung.update;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.bson.Document;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

public class UpdateFieldLiveTest {

    private static MongoClient mongoClient;
    private static MongoDatabase db;
    private static MongoCollection<Document> collection;

    @BeforeClass
    public static void setup() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDatabase("baeldung");
            collection = db.getCollection("student");

            collection.insertOne(Document.parse("{ \"student_id\": 8764,\"student_name\": \"Paul Starc\",\"address\": \"Hostel 1\",\"age\": 16,\"roll_no\":199406}"));
        }
    }

    @Test
    public void updateOne() {

        UpdateResult updateResult = collection.updateOne(Filters.eq("student_name", "Paul Starc"), Updates.set("address", "Hostel 2"));

        Document studentDetail = collection.find(Filters.eq("student_name", "Paul Starc"))
            .first();
        assertNotNull(studentDetail);
        assertFalse(studentDetail.isEmpty());

        String address = studentDetail.getString("address");
        String expectedAdderess = "Hostel 2";

        assertEquals(expectedAdderess, address);
    }

    @Test
    public void updateMany() {

        UpdateResult updateResult = collection.updateMany(Filters.lt("age", 20), Updates.set("Review", true));

        Document studentDetail = collection.find(Filters.eq("student_name", "Paul Starc"))
            .first();
        assertNotNull(studentDetail);
        assertFalse(studentDetail.isEmpty());

        Boolean review = studentDetail.getBoolean("Review");
        Boolean expectedAdderess = true;

        assertEquals(expectedAdderess, review);

    }

    @Test
    public void replaceOne() {

        Document replaceDocument = new Document();
        replaceDocument.append("student_id", 8764)
            .append("student_name", "Paul Starc")
            .append("address", "Hostel 3")
            .append("age", 18)
            .append("roll_no", 199406);

        UpdateResult updateResult = collection.replaceOne(Filters.eq("student_id", 8764), replaceDocument);

        Document studentDetail = collection.find(Filters.eq("student_name", "Paul Starc"))
            .first();
        assertNotNull(studentDetail);
        assertFalse(studentDetail.isEmpty());

        Integer age = studentDetail.getInteger("age");
        Integer expectedAge = 18;

        assertEquals(expectedAge, age);

    }

    @Test
    public void findOneAndReplace() {

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

        Document studentDetail = collection.find(Filters.eq("student_name", "Paul Starc"))
            .first();
        assertNotNull(studentDetail);
        assertFalse(studentDetail.isEmpty());

        Integer age = studentDetail.getInteger("age");
        Integer expectedAge = 18;

        assertEquals(expectedAge, age);

    }

    @Test
    public void findOneAndUpdate() {

        Document sort = new Document("roll_no", 1);
        Document projection = new Document("_id", 0).append("student_id", 1)
            .append("address", 1);
        Document resultDocument = collection.findOneAndUpdate(Filters.eq("student_id", 8764), Updates.inc("roll_no", 5), new FindOneAndUpdateOptions().upsert(true)
            .sort(sort)
            .projection(projection)
            .returnDocument(ReturnDocument.AFTER));

        Document studentDetail = collection.find(Filters.eq("student_name", "Paul Starc"))
            .first();
        assertNotNull(studentDetail);
        assertFalse(studentDetail.isEmpty());

    }

}

