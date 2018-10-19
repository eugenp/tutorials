package com.baeldung;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

import de.flapdoodle.embedmongo.MongoDBRuntime;
import de.flapdoodle.embedmongo.MongodExecutable;
import de.flapdoodle.embedmongo.MongodProcess;
import de.flapdoodle.embedmongo.config.MongodConfig;
import de.flapdoodle.embedmongo.distribution.Version;
import de.flapdoodle.embedmongo.runtime.Network;

public class AppIntegrationTest {

    private static final String DB_NAME = "myMongoDb";
    private MongodExecutable mongodExe;
    private MongodProcess mongod;
    private Mongo mongo;
    private DB db;
    private DBCollection collection;

    @Before
    public void setup() throws Exception {
        // Creating Mongodbruntime instance
        MongoDBRuntime runtime = MongoDBRuntime.getDefaultInstance();

        // Creating MongodbExecutable
        mongodExe = runtime.prepare(new MongodConfig(Version.V2_0_1, 12345, Network.localhostIsIPv6()));

        // Starting Mongodb
        mongod = mongodExe.start();
        mongo = new Mongo("localhost", 12345);

        // Creating DB
        db = mongo.getDB(DB_NAME);

        // Creating collection Object and adding values
        collection = db.getCollection("customers");
    }

    @After
    public void teardown() throws Exception {
        mongod.stop();
        mongodExe.cleanup();
    }

    @Test
    public void testAddressPersistance() {
        BasicDBObject contact = new BasicDBObject();
        contact.put("name", "John");
        contact.put("company", "Baeldung");

        // Inserting document
        collection.insert(contact);
        DBCursor cursorDoc = collection.find();
        BasicDBObject contact1 = new BasicDBObject();
        while (cursorDoc.hasNext()) {
            contact1 = (BasicDBObject) cursorDoc.next();
            System.out.println(contact1);
        }
        assertEquals(contact1.get("name"), "John");
    }
}