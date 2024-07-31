package com.baeldung.mongo.crud;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TextSearchLiveTest {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Before
    public void setUp() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            database = mongoClient.getDatabase("baeldung");
            collection = database.getCollection("user");
        }
    }

    @Test
    public void whenSearchingUserWithFullText_thenCorrectCountReturned() {
        /// WHEN
        insertUser("Leonel", "Java Spring");
        insertUser("John", "Java Spring MongoDB");
        insertUser("Smith", "Java");
        createTextIndex("description");

        // THEN
        assertEquals("Simple text search", 3, searchUser("Java Spring").size());
        assertEquals("Full text search with term Exclusion", 2, searchUser("Java Spring -MongoDB").size());
        assertEquals("All users with term Java", 1, searchUser("\"Java\"").size());
    }

    @Test
    public void whenSearchingUserWithPartialText_thenCorrectCountReturned() {
        /// WHEN
        insertUser("LEONEL", "Java Spring");
        createTextIndex("name");

        // THEN
        assertEquals("Search with capital case", 1, searchUser("LEONEL").size());
        assertEquals("Search with lower case", 1, searchUser("leonel").size());
        assertEquals("Partial search", 1, searchUser("LEONE").size());
        assertEquals("Partial search with LEO", 0, searchUser("LEO").size());
        assertEquals("Partial search with L", 0, searchUser("L").size());
    }

    public void insertUser(String name, String description) {
        Document doc = new Document("name", name).append("description", description);
        collection.insertOne(doc);
    }

    public List<Document> searchUser(String query) {
        Document result = new Document("$text", new Document("$search", query));
        return collection.find(result).into(new ArrayList<>());
    }

    public void createTextIndex(String field) {
        IndexOptions indexOptions = new IndexOptions();
        indexOptions.name("textIndex").unique(false).background(true);
        collection.createIndex(Indexes.text(field), indexOptions);
    }

    @After
    public void cleanUp() {
        mongoClient.close();
    }
}