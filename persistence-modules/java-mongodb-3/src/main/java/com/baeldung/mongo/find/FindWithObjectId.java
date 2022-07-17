package com.baeldung.mongo.find;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

public class FindWithObjectId {

    private static final String OBJECT_ID_FIELD = "_id";

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static String collectionName;
    private static String databaseName;

    public static void setUp() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost", 27017);

            databaseName = "baeldung";
            collectionName = "vehicle";

            database = mongoClient.getDatabase(databaseName);
            collection = database.getCollection(collectionName);
        }
    }

    public static void retrieveAllDocumentsUsingFindWithObjectId() {
        Document document = collection.find()
          .first();
        System.out.println(document);

        FindIterable<Document> documents = collection.find(eq(OBJECT_ID_FIELD, document.get(OBJECT_ID_FIELD)));

        MongoCursor<Document> cursor = documents.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public static void retrieveFirstDocumentWithObjectId() {
        Document document = collection.find()
          .first();
        System.out.println(document);

        FindIterable<Document> documents = collection.find(eq(OBJECT_ID_FIELD, document.get(OBJECT_ID_FIELD)));
        Document queriedDocument = documents.first();

        System.out.println(queriedDocument);
    }

    public static void retrieveDocumentWithRandomObjectId() {
        FindIterable<Document> documents = collection.find(eq(OBJECT_ID_FIELD, new ObjectId()));
        Document queriedDocument = documents.first();

        if (queriedDocument != null) {
            System.out.println(queriedDocument);
        } else {
            System.out.println("No documents found");
        }
    }

    public static void main(String args[]) {

        setUp();

        retrieveAllDocumentsUsingFindWithObjectId();

        retrieveFirstDocumentWithObjectId();

        retrieveDocumentWithRandomObjectId();
    }
}
