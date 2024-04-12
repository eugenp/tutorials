package com.baeldung.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class PushOperationLiveTest {

    private static MongoClient mongoClient;
    private static MongoDatabase db;
    private static MongoCollection<Document> collection;

    @BeforeClass
    public static void setup() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDatabase("baeldung");
            collection = db.getCollection("orders");

            collection.insertOne(
                Document.parse("{\n" + "        \"customerId\": 1023,\n" + "        \"orderTimestamp\": NumberLong(\"1646460073000\"),\n" + "        \"shippingDestination\": \"336, Street No.1 Pawai Mumbai\",\n" + "        \"purchaseOrder\": 1000,\n"
                    + "        \"contactNumber\":\"9898987676\",\n" + "        \"items\": [ \n" + "            {\n" + "                \"itemName\": \"BERGER\",\n" + "                \"quantity\": 1,\n" + "                \"price\": 500\n" + "            },\n"
                    + "            {\n" + "                \"itemName\": \"VEG PIZZA\",\n" + "                \"quantity\": 1,\n" + "                \"price\": 800\n" + "            } \n" + "          ]\n" + "    }"));
        }
    }

    @Test
    public void givenOrderCollection_whenPushOperationUsingDBObject_thenCheckingForDocument() {

        DBObject listItem = new BasicDBObject("items", new BasicDBObject("itemName", "PIZZA MANIA").append("quantity", 1)
            .append("price", 800));
        BasicDBObject searchFilter = new BasicDBObject("customerId", 1023);
        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.append("$push", listItem);
        UpdateResult updateResult = collection.updateOne(searchFilter, updateQuery);

        Document orderDetail = collection.find(Filters.eq("customerId", 1023))
            .first();
        assertNotNull(orderDetail);
        assertFalse(orderDetail.isEmpty());

    }

    @Test
    public void givenOrderCollection_whenPushOperationUsingDocument_thenCheckingForDocument() {

        Document item = new Document().append("itemName", "PIZZA MANIA")
            .append("quantity", 1)
            .append("price", 800);
        UpdateResult updateResult = collection.updateOne(Filters.eq("customerId", 1023), Updates.push("items", item));

        Document orderDetail = collection.find(Filters.eq("customerId", 1023))
            .first();
        assertNotNull(orderDetail);
        assertFalse(orderDetail.isEmpty());

    }

    @Test
    public void givenOrderCollection_whenAddToSetOperation_thenCheckingForDocument() {

        Document item = new Document().append("itemName", "PIZZA MANIA")
            .append("quantity", 1)
            .append("price", 800);
        UpdateResult updateResult = collection.updateOne(Filters.eq("customerId", 1023), Updates.addToSet("items", item));

        Document orderDetail = collection.find(Filters.eq("customerId", 1023))
            .first();
        assertNotNull(orderDetail);
        assertFalse(orderDetail.isEmpty());
    }

    @AfterClass
    public static void cleanUp() {
        mongoClient.close();
    }

}

