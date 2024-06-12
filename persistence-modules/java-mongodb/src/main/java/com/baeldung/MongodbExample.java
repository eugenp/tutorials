package com.baeldung;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;

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

    private MongoDatabase database;

    public MongodbExample(String uri) {
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            this.database = mongoClient.getDatabase("sample_mflix");
        }
    }

    public void createCollection(String collectionName) {
        try {
            database.createCollection(collectionName);
        } catch (Exception exception) {
            logger.error("Failed to create collection: {}", exception.getMessage());
        }
    }

    public void insertDocuments(MongoCollection<Document> collection) {
        collection.insertOne(new Document().append("_id", new ObjectId())
            .append("title", "Silly Video")
            .append("genres", Arrays.asList("Action", "Adventure")));

        List<Document> movieList = Arrays.asList(new Document().append("title", "Silly Video 2"), new Document().append("title", "Silly Video: The Prequel"));
        collection.insertMany(movieList);
    }

    public Document findOne(MongoCollection<Document> collection, String title) {
        return collection.find(eq("title", title))
            .first();
    }

    public Document findWithProjection(MongoCollection<Document> collection, String title) {
        Bson projectionFields = Projections.fields(Projections.include("title", "genres"), Projections.excludeId());
        return collection.find(eq("title", title))
            .projection(projectionFields)
            .sort(Sorts.ascending("year"))
            .first();
    }

    public void findMany(MongoCollection<Document> collection) {
        Bson projectionFields = Projections.fields(Projections.include("title", "year"), Projections.excludeId());
        try (MongoCursor<Document> cursor = collection.find(gt("year", 1900))
            .projection(projectionFields)
            .sort(Sorts.ascending("title"))
            .iterator()) {
            while (cursor.hasNext()) {
                logger.info(cursor.next()
                    .toJson());
            }
        }
    }

    public void updateDocument(MongoCollection<Document> collection, String title) {
        Document updateQuery = new Document().append("title", title);
        Bson updates = Updates.combine(Updates.set("runtime", 99), Updates.addToSet("genres", "Comedy"));
        collection.updateOne(updateQuery, updates);
    }

    public void updateManyDocuments(MongoCollection<Document> collection) {
        Bson updateManyQuery = gt("num_mflix_comments", 50);
        Bson updateMany = Updates.combine(Updates.addToSet("genres", "Frequently Discussed"), Updates.currentTimestamp("lastUpdated"));
        collection.updateMany(updateManyQuery, updateMany);
    }

    public void deleteDocument(MongoCollection<Document> collection, String title) {
        Bson deleteQuery = eq("title", title);
        collection.deleteOne(deleteQuery);
    }

    public void deleteManyDocuments(MongoCollection<Document> collection) {
        Bson deleteManyQuery = lt("runtime", 60);
        collection.deleteMany(deleteManyQuery);
    }
}