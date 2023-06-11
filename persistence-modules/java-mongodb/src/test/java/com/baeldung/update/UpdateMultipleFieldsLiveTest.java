package com.baeldung.update;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class UpdateMultipleFieldsLiveTest {

    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> collection;

    @Before
    public void setup() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            db = mongoClient.getDatabase("baeldung");
            collection = db.getCollection("employee");

            collection.insertOne(Document.parse("{'employee_id':794875,'employee_name': 'David Smith','job': 'Sales Representative','department_id': 2,'salary': 20000,'hire_date': NumberLong(\"1643969311817\")}"));
        }
    }

    @Test
    public void updateMultipleFieldsUsingDBObject() {

        BasicDBObject searchQuery = new BasicDBObject("employee_id", 794875);
        BasicDBObject updateFields = new BasicDBObject();
        updateFields.append("department_id", 4);
        updateFields.append("job", "Sales Manager");
        BasicDBObject setQuery = new BasicDBObject();
        setQuery.append("$set", updateFields);

        collection.updateMany(searchQuery, setQuery);

        Document nameDoc = collection.find(Filters.eq("employee_id", 794875))
            .first();
        assertNotNull(nameDoc);
        assertFalse(nameDoc.isEmpty());

        String job = nameDoc.get("job", String.class);
        assertNotNull(job);

        Integer department_id = nameDoc.get("department_id", Integer.class);
        assertNotNull(department_id);

    }

    @Test
    public void updateMultipleFieldsUsingDocument() {

        collection.updateMany(Filters.eq("employee_id", 794875), Updates.combine(Updates.set("department_id", 4), Updates.set("job", "Sales Manager")));

        Document nameDoc = collection.find(Filters.eq("employee_id", 794875))
            .first();
        assertNotNull(nameDoc);
        assertFalse(nameDoc.isEmpty());

        String job = nameDoc.get("job", String.class);
        assertNotNull(job);

        Integer department_id = nameDoc.get("department_id", Integer.class);
        assertNotNull(department_id);

    }

}

