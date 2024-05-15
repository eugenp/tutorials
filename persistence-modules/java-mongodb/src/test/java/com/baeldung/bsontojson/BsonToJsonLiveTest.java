package com.baeldung.bsontojson;

import static dev.morphia.query.experimental.filters.Filters.eq;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import dev.morphia.Datastore;
import dev.morphia.DeleteOptions;
import dev.morphia.Morphia;

public class BsonToJsonLiveTest {

    private static final String DB_NAME = "library";
    private static Datastore datastore;

    @BeforeClass
    public static void setUp() {
        datastore = Morphia.createDatastore(MongoClients.create(), DB_NAME);
        datastore.getMapper().mapPackage("com.baeldung.bsontojson");
        datastore.ensureIndexes();

        datastore.save(new Book()
            .setIsbn("isbn")
            .setTitle("title")
            .setAuthor("author")
            .setCost(3.95)
            .setPublisher(new Publisher(new ObjectId("fffffffffffffffffffffffa"),"publisher"))
            .setPublishDate(LocalDateTime.parse("2020-01-01T17:13:32Z", DateTimeFormatter.ISO_DATE_TIME))
            .addCompanionBooks(new Book().setIsbn("isbn2")));
    }

    @AfterClass
    public static void tearDown() {
        datastore.find(Book.class)
                .filter(eq("isbn", "isbn"))
                .filter(eq("title", "title"))
                .filter(eq("author", "author"))
                .filter(eq("cost", "3.95"))
                .delete(new DeleteOptions().multi(true));
    }

    @Test
    public void givenBsonDocument_whenUsingStandardJsonTransformation_thenJsonDateIsObjectEpochTime() {

        String json;
        try (MongoClient mongoClient = MongoClients.create()) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
            Document bson = mongoDatabase.getCollection("Books").find().first();
            json = bson.toJson(JsonWriterSettings
                    .builder()
                    .dateTimeConverter(new JSONDateFormatEpochTimeConverter())
                    .build());
        }

        String expectedJson = "{\"_id\": \"isbn\", " +
            "\"_t\": \"Book\", " +
            "\"title\": \"title\", " +
            "\"author\": \"author\", " +
            "\"publisher\": {\"_id\": {\"$oid\": \"fffffffffffffffffffffffa\"}, " +
            "\"_t\": \"Publisher\", \"name\": \"publisher\"}, " +
            "\"price\": 3.95, " +
            "\"publishDate\": {\"$date\": 1577898812000}}";

        assertNotNull(json);

        assertEquals(expectedJson, json);
    }

    @Test
    public void givenBsonDocument_whenUsingRelaxedJsonTransformation_thenJsonDateIsObjectIsoDate() {

        String json;
        try (MongoClient mongoClient = MongoClients.create()) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
            Document bson = mongoDatabase.getCollection("Books").find().first();
            json = bson.toJson(JsonWriterSettings
                .builder()
                .outputMode(JsonMode.RELAXED)
                .build());
        }

        String expectedJson = "{\"_id\": \"isbn\", " +
            "\"_t\": \"Book\", " +
            "\"title\": \"title\", " +
            "\"author\": \"author\", " +
            "\"publisher\": {\"_id\": {\"$oid\": \"fffffffffffffffffffffffa\"}, " +
            "\"_t\": \"Publisher\", \"name\": \"publisher\"}, " +
            "\"price\": 3.95, " +
            "\"publishDate\": {\"$date\": \"2020-01-01T17:13:32Z\"}}";

        assertNotNull(json);

        assertEquals(expectedJson, json);
    }

    @Test
    public void givenBsonDocument_whenUsingCustomJsonTransformation_thenJsonDateIsStringField() {

        String json;
        try (MongoClient mongoClient = MongoClients.create()) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
            Document bson = mongoDatabase.getCollection("Books").find().first();
            json = bson.toJson(JsonWriterSettings
                .builder()
                .dateTimeConverter(new JsonDateTimeConverter())
                .build());
        }

        String expectedJson = "{\"_id\": \"isbn\", " +
            "\"_t\": \"Book\", " +
            "\"title\": \"title\", " +
            "\"author\": \"author\", " +
            "\"publisher\": {\"_id\": {\"$oid\": \"fffffffffffffffffffffffa\"}, " +
            "\"_t\": \"Publisher\", \"name\": \"publisher\"}, " +
            "\"price\": 3.95, " +
            "\"publishDate\": \"2020-01-01T17:13:32Z\"}";

        assertEquals(expectedJson, json);

    }

}
