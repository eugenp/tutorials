package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Sorts.*;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Testcontainers
public class MongoDBCrudOperationsLiveTest {
	
	private static final int REQUIRED_DOCUMENT_COUNT = 250;

	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	@Container
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");


    @BeforeEach
    public void setup() {
        mongoDBContainer.start();
        mongoClient = MongoClients.create(mongoDBContainer.getReplicaSetUrl());
        database = mongoClient.getDatabase("sample_mflix");
        collection = database.getCollection("movies");
    }
    
    @AfterEach
    public void tearDown() {
        if (mongoClient != null) {
            mongoClient.close();
        }
        if (mongoDBContainer != null) {
            mongoDBContainer.stop();
        }
    }


    @Test
    public void testInsertSingleDocument() {
        ObjectId objectId = new ObjectId();
        Document newMovie = new Document("_id", objectId)
            .append("title", "Silly Video")
            .append("genres", Arrays.asList("Action", "Adventure"));

        // Perform the insert and capture the result
        InsertOneResult result = collection.insertOne(newMovie);
        assertTrue(result.wasAcknowledged(), "Insert operation should be acknowledged");

        // Retrieve the document to verify insertion
        Document found = collection.find(eq("_id", objectId)).first();
        assertNotNull(found, "Document should be found in the database");
        assertEquals("Silly Video", found.getString("title"), "Titles should match");
    }

    @Test
    public void testInsertMultipleDocuments() {
        List<Document> movies = Arrays.asList(
            new Document().append("title", "Silly Video 2"),
            new Document().append("title", "Silly Video: The Prequel")
        );

        // Perform the insert and capture the result
        InsertManyResult result = collection.insertMany(movies);
        assertTrue(result.wasAcknowledged(), "Insert operation should be acknowledged");

        // Retrieve the documents to verify insertion
        Document foundMovie1 = collection.find(new Document("title", "Silly Video 2")).first();
        Document foundMovie2 = collection.find(new Document("title", "Silly Video: The Prequel")).first();

        assertNotNull(foundMovie1, "First document should be found in the database");
        assertNotNull(foundMovie2, "Second document should be found in the database");
    }
	
	@Test
	public void testSimpleFindDocument() {
		Document found = collection.find(eq("title", "The Great Train Robbery"))
				.first();
		
		assertNotNull(found);
		assertEquals("The Great Train Robbery", found.getString("title"));
	}

	@Test
	public void testFindDocumentWithProjectionAndSort() {
		Bson projection = Projections.fields(
				Projections.include("title", "genres"), 
				Projections.excludeId());
		
		Document found = collection.find(eq("title", "The Great Train Robbery"))
				.projection(projection)
				.sort(ascending("year"))
				.first();
		
		assertNotNull(found);
		assertEquals("The Great Train Robbery", found.getString("title"));
	}

	@Test
	public void testFindManyWithProjectionAndSort() {
		Bson projection = Projections.fields(Projections.include("title", "year"), 
				Projections.excludeId());

		MongoCursor<Document> cursor = collection.find(gt("year", 1900))
				.projection(projection)
				.sort(Sorts.ascending("title"))
				.iterator();
		
		List<String> titles = new ArrayList<>();
		try {
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				titles.add(doc.getString("title"));
				assertNotNull(doc.getString("title")); // Ensure title is present
				assertNotNull(doc.getInteger("year")); // Ensure year is present
				assertNull(doc.get("_id")); // Ensure _id is excluded
			}
		} finally {
			cursor.close();
		}
		
		// Verify that the titles are sorted correctly
		if (titles.size() > 1) {
			for (int i = 0; i < titles.size() - 1; i++) {
				assertTrue(titles.get(i).compareTo(titles.get(i + 1)) <= 0);
			}
		}
	}

	@Test
	public void testUpdateDocument() {
	    // Ensure the document is in a known state
	    collection.deleteMany(eq("title", "Silly Video"));  // Clear existing
	    collection.insertOne(new Document("title", "Silly Video").append("runtime", 95));  // Ensure it exists for update

	    Bson updateQuery = eq("title", "Silly Video");
	    Bson updates = combine(set("runtime", 99), addToSet("genres", "Comedy"), currentTimestamp("lastUpdated"));

	    UpdateResult result = collection.updateOne(updateQuery, updates);
	    assertTrue(result.getModifiedCount() > 0, "Should modify at least one document");
	}

	@Test
	public void testUpdateManyDocuments() {
	    prepareSeedDataIfNeeded("num_mflix_comments", 50, REQUIRED_DOCUMENT_COUNT);

	    Bson query = gt("num_mflix_comments", 50);
	    Bson updates = combine(
	        addToSet("genres", "Frequently Discussed"), 
	        currentTimestamp("lastUpdated")
	    );

	    UpdateResult result = collection.updateMany(query, updates);
	    assertTrue(result.getModifiedCount() > 0, "Expected to modify at least one document.");

	    long updatedCount = collection.countDocuments(
	        and(gt("num_mflix_comments", 50), in("genres", "Frequently Discussed"))
	    );
	    assertEquals(result.getModifiedCount(), updatedCount, "Expected modified document count to match actual.");
	}

	private void prepareSeedDataIfNeeded(String field, int minValue, int targetCount) {
	    long currentCount = collection.countDocuments(gt(field, minValue));
	    if (currentCount < targetCount) {
	        List<Document> seedData = new ArrayList<>();
	        for (int i = 0; i < targetCount - currentCount; i++) {
	            seedData.add(new Document(field, minValue + 1).append("title", "Comment Heavy Video " + i));
	        }
	        collection.insertMany(seedData);
	    }
	}


	@Test
	public void testDeleteDocument() {
	    // Ensure a clean state before test
	    collection.deleteMany(eq("title", "Silly Video"));  // Clear any existing entries
	    collection.insertOne(new Document("title", "Silly Video").append("genre", "Comedy"));  // Re-insert the document for a controlled test environment

	    DeleteResult result = collection.deleteOne(eq("title", "Silly Video"));
	    assertEquals(1, result.getDeletedCount(), "Exactly one document should have been deleted");

	    long remainingCount = collection.countDocuments(eq("title", "Silly Video"));
	    assertEquals(0, remainingCount, "No documents should remain with the title 'Silly Video'");
	}
	
	@Test
	public void testDeleteManyDocuments() {
	    // Ensure the presence of documents to delete
	    collection.insertOne(new Document("title", "Short Film").append("runtime", 55));
	    collection.insertOne(new Document("title", "Even Shorter Film").append("runtime", 45));

	    long initialCount = collection.countDocuments(lt("runtime", 60));
	    assertTrue(initialCount > 0, "There should be documents to delete");

	    DeleteResult result = collection.deleteMany(lt("runtime", 60));
	    assertTrue(result.getDeletedCount() > 0, "Should delete at least one document");
	    assertEquals(0, collection.countDocuments(lt("runtime", 60)), "No documents should remain that match the query");
	}
}
