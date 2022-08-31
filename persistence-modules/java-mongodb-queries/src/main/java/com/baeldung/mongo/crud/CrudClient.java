package com.baeldung.mongo.crud;

import com.baeldung.mongo.crud.model.Event;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lt;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class CrudClient {

    private static String uri = "mongodb://localhost:27017";
    private static MongoClient mongoClient = MongoClients.create(uri);
    private static MongoDatabase db;
    private static MongoCollection<Event> collection;

    public static OffsetDateTime offsetDateTime = OffsetDateTime.of(LocalDateTime.of(2022, 6, 4, 11, 0, 0),
            ZoneOffset.ofHours(2));

    public static Event pianoLessons = new Event(
            "Piano lessons",
            "Foo Bvld",
            LocalDateTime.of(2022, 6, 4, 11, 0, 0));

    public static Event soccerGame = new Event(
            "Soccer game",
            "Bar Avenue",
            LocalDateTime.of(2022, 6, 10, 17, 0, 0));

    public static Event pianoLessonsTZ = new Event(
            "Piano lessons",
            "Baz Bvld",
            LocalDateTime.of(2022, 12, 31, 12, 0, 0),
            ZoneOffset.ofHours(2).toString());

    public static LocalDateTime dateQuery = LocalDateTime.of(2022, 6, 10, 17, 0, 0);
    public static LocalDateTime dateQueryEventWithTZ = LocalDateTime.of(2022, 12, 31, 12, 0, 0);

    public static LocalDateTime from = LocalDateTime.of(2022, 06, 04, 12, 0, 0);
    public static LocalDateTime to = LocalDateTime.of(2022, 06, 10, 17, 0, 0);

    public static LocalDate updateManyFrom = LocalDate.of(2022, 1, 1);
    public static LocalDate updateManyTo = LocalDate.of(2023, 1, 1);

    public static LocalDate deleteFrom = LocalDate.of(2022, 1, 1);
    public static LocalDate deleteTo = LocalDate.of(2023, 01, 01);

    public static void setup() {
        CodecProvider codecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry codecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(codecProvider));

        db = mongoClient.getDatabase("calendar").withCodecRegistry(codecRegistry);
        collection = db.getCollection("my_events", Event.class);
    }

    public static void close() {
        mongoClient.close();
    }

    public static BsonValue insertEventsWithDate(Event e) {
        try {
            InsertOneResult insertResult = collection.insertOne(e);
            return insertResult.getInsertedId();
        } catch (MongoException me) {
            System.err.println("Failed to insert with error: " + me);
            throw me;
        }
    }

    public static Event readEventsByDate(LocalDateTime localDateTime) {
        try {
            Event event = collection
                    .find(eq("dateTime", localDateTime))
                    .first();
            return event;
        } catch (MongoException me) {
            System.err.println("Failed to read with error: " + me);
            throw me;
        }
    }

    public static List<Event> readEventsByDateRange(LocalDateTime from, LocalDateTime to) {
        BasicDBObject object = new BasicDBObject();
        object.put("dateTime", BasicDBObjectBuilder
                .start("$gte", from)
                .add("$lte", to)
                .get());
        try {
            List<Event> list = new ArrayList<Event>(collection.find(object).into(new ArrayList<Event>()));
            return list;
        } catch (MongoException me) {
            System.err.println("Failed to read with error: " + me);
            throw me;
        }
    }

    public static LocalDateTime readEventsByDateWithTZ(LocalDateTime localDateTime) {
        try {
            Event event = collection
                    .find(eq("dateTime", localDateTime))
                    .first();
            OffsetDateTime offsetDateTime = OffsetDateTime.of(event.dateTime, ZoneOffset.of(pianoLessonsTZ.timeZoneOffset));
            ZonedDateTime zoned = offsetDateTime.atZoneSameInstant(ZoneId.of("America/Toronto"));
            return zoned.toLocalDateTime();
        } catch (MongoException me) {
            System.err.println("Failed to read with error: " + me);
            throw me;
        }
    }

    public static long updateDateField() {
        Document document = new Document().append("title", "Piano lessons");
        Bson update = Updates.currentDate("updatedAt");
        UpdateOptions options = new UpdateOptions().upsert(false);
        try {
            UpdateResult result = collection.updateOne(document, update, options);
            return result.getModifiedCount();
        } catch (MongoException me) {
            System.err.println("Failed to update with error: " + me);
            throw me;
        }
    }

    public static long updateManyEventsWithDateCriteria(LocalDate updateManyFrom, LocalDate updateManyTo) {
        Bson query = and(gte("dateTime", updateManyFrom), lt("dateTime", updateManyTo));
        Bson updates = Updates.currentDate("dateTime");
        try {
            UpdateResult result = collection.updateMany(query, updates);
            return result.getModifiedCount();
        } catch(MongoException me) {
            System.err.println("Failed to replace/update with error: " + me);
            throw me;
        }
    }

    public static long deleteEventsByDate(LocalDate from, LocalDate to) {
        Bson query = and(gte("dateTime", from), lt("dateTime", to));
        try {
            DeleteResult result = collection.deleteMany(query);
            return result.getDeletedCount();
        } catch (MongoException me) {
            System.err.println("Failed to delete with error: " + me);
            throw me;
        }
    }

    public static void dropDb() {
        db.drop();
    }

    public static void main(String[] args) {
    }
}
