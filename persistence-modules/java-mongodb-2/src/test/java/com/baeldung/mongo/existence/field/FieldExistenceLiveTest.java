package com.baeldung.mongo.existence.field;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FieldExistenceLiveTest {
    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> collection;

    @Before
    public void setup() {
        if (mongoClient == null) {
            mongoClient = new MongoClient();
            db = mongoClient.getDatabase("existence");
            collection = db.getCollection("users");
            collection.insertOne(Document.parse("{'name':'Ben','surname': 'Big'}"));
        }
    }

    @Test
    public void givenMongoCollection_whenUsingFilters_thenCheckingForExistingFieldWorks() {
        Document nameDoc = collection.find(Filters.exists("name")).first();
        assertNotNull(nameDoc);
        assertFalse(nameDoc.isEmpty());

        nameDoc = collection.find(Filters.exists("non_existing")).first();
        assertNull(nameDoc);
    }

    @Test
    public void givenMongoCollection_whenUsingStandardDocumentQuery_thenCheckingForExistingFieldWorks() {
        Document query = new Document("name", new BasicDBObject("$exists", true));
        Document doc = collection.find(query).first();
        assertNotNull(doc);
        assertFalse(doc.isEmpty());

        query = new Document("non_existing", new BasicDBObject("$exists", true));
        doc = collection.find(query).first();
        assertNull(doc);
    }
}
