package com.baeldung.tailablecursor.service;

import com.baeldung.tailablecursor.domain.Log;
import com.baeldung.tailablecursor.domain.LogLevel;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.SocketUtils;

import java.io.IOException;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ErrorLogsCounterManualTest {

    private static final String SERVER = "localhost";
    private static final int PORT = SocketUtils.findAvailableTcpPort(10000);
    private static final String DB_NAME = "test";
    private static final String COLLECTION_NAME = Log.class.getName().toLowerCase();

    private static final MongodStarter starter = MongodStarter.getDefaultInstance();
    private static final int MAX_DOCUMENTS_IN_COLLECTION = 3;

    private ErrorLogsCounter errorLogsCounter;
    private MongodExecutable mongodExecutable;
    private MongodProcess mongoDaemon;
    private MongoDatabase db;

    @Before
    public void setup() throws Exception {
        MongoTemplate mongoTemplate = initMongoTemplate();

        MongoCollection<Document> collection = createCappedCollection();

        persistDocument(collection, -1, LogLevel.ERROR, "my-service", "Initial log");

        errorLogsCounter = new ErrorLogsCounter(mongoTemplate, COLLECTION_NAME);
        Thread.sleep(1000L); // wait for initialization
    }

    private MongoTemplate initMongoTemplate() throws IOException {
        mongodExecutable = starter.prepare(new MongodConfigBuilder()
          .version(Version.Main.PRODUCTION)
          .net(new Net(SERVER, PORT, Network.localhostIsIPv6()))
          .build());
        mongoDaemon = mongodExecutable.start();

        MongoClient mongoClient = new MongoClient(SERVER, PORT);
        db = mongoClient.getDatabase(DB_NAME);

        return new MongoTemplate(mongoClient, DB_NAME);
    }

    private MongoCollection<Document> createCappedCollection() {
        db.createCollection(COLLECTION_NAME, new CreateCollectionOptions()
          .capped(true)
          .sizeInBytes(100000)
          .maxDocuments(MAX_DOCUMENTS_IN_COLLECTION));
        return db.getCollection(COLLECTION_NAME);
    }

    private void persistDocument(MongoCollection<Document> collection,
                                 int i, LogLevel level, String service, String message) {
        Document logMessage = new Document();
        logMessage.append("_id", i);
        logMessage.append("level", level.toString());
        logMessage.append("service", service);
        logMessage.append("message", message);
        collection.insertOne(logMessage);
    }

    @After
    public void tearDown() {
        errorLogsCounter.close();
        mongoDaemon.stop();
        mongodExecutable.stop();
    }

    @Test
    public void whenErrorLogsArePersisted_thenTheyAreReceivedByLogsCounter() throws Exception {
        MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);

        IntStream.range(1, 10)
          .forEach(i -> persistDocument(collection,
            i,
            i > 5 ? LogLevel.ERROR : LogLevel.INFO,
            "service" + i,
            "Message from service " + i)
          );

        Thread.sleep(1000L); // wait to receive all messages from the reactive mongodb driver
		
        assertThat(collection.countDocuments(), is((long) MAX_DOCUMENTS_IN_COLLECTION));
        assertThat(errorLogsCounter.count(), is(5));
    }

}
