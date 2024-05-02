package com.baeldung;

import static com.mongodb.client.model.Filters.*;

import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;

public class MongodbExample {
	
	private static final Logger logger = LoggerFactory.getLogger(MongodbExample.class);

	public static void main(String[] args) {

		// 
		// Section 3.2
		//
		
		// Connect with MongoClient
		// Configure the environment variable with your own MongoDB Connection String
		
		String uri = System.getenv("MONGODB_URI") != null ? System.getenv("MONGODB_URI") : "mongodb://localhost:27017";

		try (MongoClient mongoClient = MongoClients.create(uri)) {
			
			//
			// Section 3.3
			// 
			
			// Connecting to your database
			MongoDatabase database = mongoClient.getDatabase("sample_mflix");

			
			
			//
			// Section 3.4
			//
			
			// create a collection called movies, if one does not already exist
			try {
			    database.createCollection("movies");
			} catch (Exception exception) {
				logger.error("Failed to create collection: {}", exception.getMessage());
			}

			MongoCollection<Document> collection = database.getCollection("movies");

			
			
			// 
			// Section 3.5
			//
			
			// Insert
			// Inserts a sample document describing a movie into the collection
			collection.insertOne(new Document()
					.append("_id", new ObjectId())
					.append("title", "Silly Video")
					.append("genres", Arrays.asList("Action", "Adventure")));

			// Insert Many
			// Inserts a list of documents into the movies collection
			List<Document> movieList = Arrays.asList(
					  new Document().append("title", "Silly Video 2"),
					  new Document().append("title", "Silly Video: The Prequel"));

			collection.insertMany(movieList);
			
			
			
			// 
			// Section 3.6
			// 
			
			// Find one
			Document doc1 = collection.find(eq("title", "The Great Train Robbery")) .first();
			
            if (doc1 == null) {
                logger.info("No results found.");
            } else {
                logger.info(doc1.toJson());
            }
			
			// Find with projection
			// Creates instructions to project two document fields
			Bson projectionFields = Projections.fields(
					Projections.include("title", "genres"), 
					Projections.excludeId());

			// Retrieves the first matching document, applying a projection and a descending
			// sort to the results
			Document doc2 = collection.find(eq("title", "The Great Train Robbery"))
					.projection(projectionFields)
					.sort(Sorts.ascending("year"))
					.first();

            if (doc2 == null) {
                logger.info("No results found.");
            } else {
                logger.info(doc2.toJson());
            }

			// Find many
			projectionFields = Projections.fields(
					Projections.include("title", "year"), 
					Projections.excludeId());

			MongoCursor<Document> cursor = collection.find(gt("year", 1900))
					.projection(projectionFields)
					.sort(Sorts.ascending("title")).iterator();

            try {
                while (cursor.hasNext()) {
                    logger.info(cursor.next().toJson());
                }
            } finally {
                cursor.close();
            }
            
            

            //
            // Section 3.7
            //
            
			// Update a document with the title "Silly Video"
			Document updateQuery = new Document().append("title", "Silly Video");

			// Creates instructions to update the values of two document fields
			Bson updates = Updates.combine(
					Updates.set("runtime", 99), 
					Updates.addToSet("genres", "Comedy"));

			// Updates the first document that has a "title" value of "Silly Video"
			collection.updateOne(updateQuery, updates);

			// Update Many
			Bson updateManyQuery = gt("num_mflix_comments", 50); 
			Bson updateMany = Updates.combine( 
					Updates.addToSet("genres", "Frequently Discussed"),
					Updates.currentTimestamp("lastUpdated")); 
			
			collection.updateMany(updateManyQuery, updateMany);
			
			
			
			
			//
			// Section 3.8
			//
			
			// Delete the first document matching with the title "Silly Video"
			Bson deleteQuery = eq("title", "Silly Video");

			collection.deleteOne(deleteQuery);
			
			// Delete Many
			// deletes all documents with a runtime value less than 60
			Bson deleteManyQuery = lt("runtime", 60);
			
			collection.deleteMany(deleteManyQuery);

		}
	}

}
