package com.baeldung;

import static org.junit.Assert.assertEquals;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.transitions.Mongod;
import de.flapdoodle.embed.mongo.transitions.RunningMongodProcess;
import de.flapdoodle.reverse.TransitionWalker;

public class AppLiveTest {

    private static final String DB_NAME = "myMongoDb";

    @Test
    public void testAddressPersistance() {
        try (TransitionWalker.ReachedState<RunningMongodProcess> running = Mongod.instance().start(Version.V6_0_3)) {
            try (MongoClient mongo = MongoClients.create("mongodb://" + running.current().getServerAddress().getHost() + ":" + running.current().getServerAddress().getPort())) {
                // Creating DB
                MongoDatabase db = mongo.getDatabase(DB_NAME);

                // Creating collection Object and adding values
                MongoCollection<Document> collection = db.getCollection("customers");

                Document contact = new Document();
                contact.put("name", "John");
                contact.put("company", "Baeldung");

                // Inserting document
                collection.insertOne(contact);
                FindIterable<Document> cursorDoc = collection.find();
                Document contact1 = new Document();
                final MongoCursor<Document> cursor = cursorDoc.cursor();
                while (cursor.hasNext()) {
                    contact1 = cursor.next();
                    System.out.println(contact1);
                }
                assertEquals(contact1.get("name"), "John");
            }
        }
    }
}