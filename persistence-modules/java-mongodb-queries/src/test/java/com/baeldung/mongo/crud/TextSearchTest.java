package com.baeldung.mongo.crud;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TextSearchTest {
    private static MongoDBClient mongoDBClient;

    @BeforeClass
    public static void setup() {
        mongoDBClient = new MongoDBClient("mongodb://localhost:27017", "baeldung", "user");
    }

    @Test
    public void whenSearchingUserWithFullText_thenCorrectCountReturned() {
        /// WHEN
        mongoDBClient.insertUser("Leonel", "Java Spring");
        mongoDBClient.insertUser("John", "Java Spring MongoDB");
        mongoDBClient.insertUser("Smith", "Java");
        mongoDBClient.createTextIndex("description");

        // THEN
        assertEquals("Simple text search", 3, mongoDBClient.searchUser("Java Spring").size());
        assertEquals("Full text search with term Exclusion", 2, mongoDBClient.searchUser("Java Spring -MongoDB").size());
        assertEquals("All users with term Java", 1, mongoDBClient.searchUser("\"Java\"").size());
    }

    @Test
    public void whenSearchingUserWithPartialText_thenCorrectCountReturned() {
        /// WHEN
        mongoDBClient.insertUser("LEONEL", "Java Spring");
        mongoDBClient.createTextIndex("name");

        // THEN
        assertEquals("Search with capital case", 1, mongoDBClient.searchUser("LEONEL").size());
        assertEquals("Search with lower case", 1, mongoDBClient.searchUser("leonel").size());
        assertEquals("Partial search", 1, mongoDBClient.searchUser("LEONE").size());
        assertEquals("Partial search with LEO", 0, mongoDBClient.searchUser("LEO").size());
        assertEquals("Partial search with L", 0, mongoDBClient.searchUser("L").size());
    }


    private static class MongoDBClient {
        private MongoClient mongoClient;
        private MongoDatabase db;
        private MongoCollection<Document> collection;

        public MongoDBClient(String connectionString, String dbName, String collectionName) {
            mongoClient = MongoClients.create(connectionString);
            db = mongoClient.getDatabase(dbName);
            collection = db.getCollection(collectionName);
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
    }
}
