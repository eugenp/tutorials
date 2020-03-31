package com.baeldung.json;

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
import org.bson.json.JsonWriterSettings;
import org.bson.json.StrictJsonWriter;
import org.bson.types.ObjectId;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.morphia.domain.Book;
import com.baeldung.morphia.domain.Publisher;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import dev.morphia.Datastore;
import dev.morphia.Morphia;

public class JsonSerializationIntegrationTest {

    private static final String DB_NAME = "library";
    private static Datastore datastore;

    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        .withZone(TimeZone.getDefault()
        .toZoneId());
    
    private static final String DATE_STRING = "2020-01-01T18:13:32+01:00";

    private static final Book BOOK = new Book()
        .setIsbn("isbn")
        .setTitle("title")
        .setAuthor("author")
        .setCost(3.95)
        .setPublisher(new Publisher(new ObjectId(),"publisher"))
        .setPublishDate(LocalDateTime.parse(DATE_STRING, DATE_TIME_FORMATTER))
        .addCompanionBooks(new Book().setIsbn("isbn2"));


    @BeforeClass
    public static void setUp() {
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.baeldung.morphia");
        datastore = morphia.createDatastore(new MongoClient(), DB_NAME);
        datastore.delete(datastore.createQuery(Book.class));
        datastore.ensureIndexes();
        datastore.save(BOOK);
    }


    @Test
    public void givenBsonDocument_whenUsingStandardJsonTransformation_thenJsonDateIsTimestamp() {

        String json = null;
        try (MongoClient mongoClient = new MongoClient()) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
            Document bson = mongoDatabase.getCollection("Books")
                .find()
                .first();
            json = bson.toJson();
        }
        
//        String expectedJson = '''
//        {"_id": "isbn", "className": "com.baeldung.morphia.domain.Book", "title": "title", "author": "author", "publisher": {"_id": {"$oid": "5e830c89aad3d41a51fab776"}, "name": "publisher"}, "price": 3.95, "publishDate": {"$date": 1577898812000}}11:25:31.006 [main] INFO  org.mongodb.driver.cluster - Cluster created with settings {hosts=[127.0.0.1:27017], mode=SINGLE, requiredClusterType=UNKNOWN, serverSelectionTimeout='30000 ms', maxWaitQueueSize=500}
//        ''';

        assertNotNull(json);

        String jsonTitle = extractJsonField(json, "title");
        assertEquals("\"" + BOOK.getTitle() + "\"", jsonTitle);

        String jsonAuthor = extractJsonField(json, "author");
        assertEquals("\"" + BOOK.getAuthor() + "\"", jsonAuthor);

        String jsonCost = extractJsonField(json, "price");
        assertEquals(Double.toString(BOOK.getCost()), jsonCost);

        String jsonTimestamp = extractJsonField(json, "publishDate");
        String expectedTimestamp = String.format("{\"$date\": %d", Timestamp.valueOf(BOOK.getPublishDate()).getTime());
        assertEquals(expectedTimestamp, jsonTimestamp);
    }

    String extractJsonField(String json, String fieldName) {
        Pattern p = Pattern.compile(String.format(".*\"%s\"\\s*:\\s*(.+?)[,}].*", fieldName));
        Matcher m = p.matcher(json);
        assertTrue(m.matches());
        return m.group(1)
            .trim();
    }

    private static class JsonDateTimeConverter implements Converter<Long> {

        private static final Logger LOGGER = LoggerFactory.getLogger(JsonDateTimeConverter.class);

        @Override
        public void convert(Long value, StrictJsonWriter writer) {
            try {
                Instant instant = new Date(value).toInstant();
                String s = DATE_TIME_FORMATTER.format(instant);
                writer.writeString(s);
            } catch (Exception e) {
                LOGGER.error(String.format("Fail to convert offset %d to JSON date", value), e);
            }
        }
    }
    
    @Test
    public void givenBsonDocument_whenUsingCustomJsonTransformation_thenJsonDateIsWellFormatted() {

        JsonWriterSettings writerSettings = JsonWriterSettings.builder()
            .dateTimeConverter(new JsonDateTimeConverter())
            .build();

        String json = null;
        try (MongoClient mongoClient = new MongoClient()) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(DB_NAME);
            Document bson = mongoDatabase.getCollection("Books")
                .find()
                .first();
            json = bson.toJson(writerSettings);
        }

        String jsonDate = extractJsonField(json, "publishDate");
        assertEquals("\"" + DATE_STRING + "\"", jsonDate);

    }

}
