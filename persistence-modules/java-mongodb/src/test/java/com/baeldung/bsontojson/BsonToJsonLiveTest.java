package com.baeldung.bsontojson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.json.Converter;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.bson.json.StrictJsonWriter;
import org.bson.types.ObjectId;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.morphia.domain.Book;
import com.baeldung.morphia.domain.Publisher;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import dev.morphia.Datastore;
import dev.morphia.Morphia;

public class BsonToJsonLiveTest {
    
    private static final String DB_NAME = "library";
    private static Datastore datastore;

    @BeforeClass
    public static void setUp() {
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.baeldung.morphia");
        datastore = morphia.createDatastore(new MongoClient(), DB_NAME);
        datastore.ensureIndexes();
        
        datastore.save(new Book()
            .setIsbn("isbn")
            .setTitle("title")
            .setAuthor("author")
            .setCost(3.95)
            .setPublisher(new Publisher(new ObjectId("fffffffffffffffffffffffa"),"publisher"))
            .setPublishDate(LocalDateTime.parse("2020-01-01T18:13:32Z", DateTimeFormatter.ISO_DATE_TIME))
            .addCompanionBooks(new Book().setIsbn("isbn2")));
    }
    
    @AfterClass
    public static void tearDown() {
        datastore.delete(datastore.createQuery(Book.class));
    }

    @Test
    public void givenBsonDocument_whenUsingStandardJsonTransformation_thenJsonDateIsObjectEpochTime() {

        String json = null;
        try (MongoClient mongoClient = new MongoClient()) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
            Document bson = mongoDatabase.getCollection("Books").find().first();
            json = bson.toJson();
        }
        
        String expectedJson = "{\"_id\": \"isbn\", " + 
            "\"className\": \"com.baeldung.morphia.domain.Book\", " + 
            "\"title\": \"title\", " + 
            "\"author\": \"author\", " + 
            "\"publisher\": {\"_id\": {\"$oid\": \"fffffffffffffffffffffffa\"}, " + 
            "\"name\": \"publisher\"}, " + 
            "\"price\": 3.95, " + 
            "\"publishDate\": {\"$date\": 1577898812000}}";

        assertNotNull(json);
        
        assertEquals(expectedJson, json);
    }
    

    @Test
    public void givenBsonDocument_whenUsingRelaxedJsonTransformation_thenJsonDateIsObjectIsoDate() {
   
        String json = null;
        try (MongoClient mongoClient = new MongoClient()) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
            Document bson = mongoDatabase.getCollection("Books").find().first();
            json = bson.toJson(JsonWriterSettings
                .builder()
                .outputMode(JsonMode.RELAXED)
                .build());
        }
        
        String expectedJson = "{\"_id\": \"isbn\", " + 
            "\"className\": \"com.baeldung.morphia.domain.Book\", " + 
            "\"title\": \"title\", " + 
            "\"author\": \"author\", " + 
            "\"publisher\": {\"_id\": {\"$oid\": \"fffffffffffffffffffffffa\"}, " + 
            "\"name\": \"publisher\"}, " + 
            "\"price\": 3.95, " + 
            "\"publishDate\": {\"$date\": \"2020-01-01T17:13:32Z\"}}";

        assertNotNull(json);
        
        assertEquals(expectedJson, json);
    }
    
    @Test
    public void givenBsonDocument_whenUsingCustomJsonTransformation_thenJsonDateIsStringField() {

        String json = null;
        try (MongoClient mongoClient = new MongoClient()) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
            Document bson = mongoDatabase.getCollection("Books").find().first();
            json = bson.toJson(JsonWriterSettings
                .builder()
                .dateTimeConverter(new JsonDateTimeConverter())
                .build());
        }

        String expectedJson = "{\"_id\": \"isbn\", " + 
            "\"className\": \"com.baeldung.morphia.domain.Book\", " + 
            "\"title\": \"title\", " + 
            "\"author\": \"author\", " + 
            "\"publisher\": {\"_id\": {\"$oid\": \"fffffffffffffffffffffffa\"}, " + 
            "\"name\": \"publisher\"}, " + 
            "\"price\": 3.95, " + 
            "\"publishDate\": \"2020-01-01T17:13:32Z\"}";

        assertEquals(expectedJson, json);

    }

}
