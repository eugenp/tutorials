package com.baeldung.update;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.bson.Document;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

public class PustSetOperationLiveTest {

    private static MongoClient mongoClient;
    private static MongoDatabase db;
    private static MongoCollection<Document> collection;

    @BeforeClass
    public static void setup() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDatabase("baeldung");
            collection = db.getCollection("marks");

            collection.insertOne(Document.parse("{\n" + "        \"studentId\": 1023,\n" + "        \"studentName\":\"James Broad\",\n" + "        \"joiningYear\":\"2018\",\n" + "        \"totalMarks\":100,\n" + "        \"subjectDetails\":[\n"
                + "            {\n" + "                \"subjectId\":123,\n" + "                \"subjectName\":\"Operating Systems Concepts\",\n" + "                \"marks\":4,\n" + "            },\n" + "            {\n"
                + "                \"subjectId\":124,\n" + "                \"subjectName\":\"Numerical Analysis\",\n" + "                \"marks\":60\n" + "            }\n" + "        ]\n" + "    }"));

        }
    }

    @Test
    public void givenMarksCollection_whenPushSetOperation_thenCheckingForDocument() {

        Document subjectData = new Document().append("subjectId", 126)
            .append("subjectName", "Java Programming")
            .append("marks", 70);
        UpdateResult updateQueryResult = collection.updateOne(Filters.eq("studentId", 1023), Updates.combine(Updates.set("totalMarks", 170), Updates.push("subjectDetails", subjectData)));

        Document studentDetail = collection.find(Filters.eq("studentId", 1023))
            .first();
        assertNotNull(studentDetail);
        assertFalse(studentDetail.isEmpty());

    }

}

