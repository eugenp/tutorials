package com.baeldung.mongo.aggregation;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import static com.mongodb.client.model.Aggregates.count;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.out;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.sort;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AggregationLiveTest {

    private static final String DATABASE = "world";
    private static final String COLLECTION = "countries";
    private static final String DATASET_JSON = "/countrydata.json";
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;

    @BeforeClass
    public static void setUpDB() throws IOException {
        mongoClient = MongoClients.create();
        database = mongoClient.getDatabase(DATABASE);
        collection = database.getCollection(COLLECTION);

        collection.drop();

        InputStream is = AggregationLiveTest.class.getResourceAsStream(DATASET_JSON);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        reader.lines()
            .forEach(line -> collection.insertOne(Document.parse(line)));
        reader.close();
    }

    @Test
    public void givenCountryCollection_whenEnglishSpeakingCountriesCounted_thenNinetyOne() {
        Document englishSpeakingCountries = collection.aggregate(Arrays.asList(match(Filters.eq("languages.name", "English")), count()))
            .first();

        assertEquals(91, englishSpeakingCountries.get("count"));
    }

    @Test
    public void givenCountryCollection_whenAreaSortedDescending_thenSuccess() {

        collection.aggregate(Arrays.asList(sort(Sorts.descending("area")), limit(7), out("largest_seven")))
            .toCollection();

        MongoCollection<Document> largestSeven = database.getCollection("largest_seven");

        assertEquals(7, largestSeven.countDocuments());

        Document usa = largestSeven.find(Filters.eq("alpha3Code", "USA"))
            .first();

        assertNotNull(usa);
    }

    @Test
    public void givenCountryCollection_whenCountedRegionWise_thenMaxInAfrica() {
        Document maxCountriedRegion = collection.aggregate(Arrays.asList(group("$region", Accumulators.sum("tally", 1)), sort(Sorts.descending("tally"))))
            .first();
        assertTrue(maxCountriedRegion.containsValue("Africa"));
    }

    @Test
    public void givenCountryCollection_whenNeighborsCalculated_thenMaxIsFifteenInChina() {
        Bson borderingCountriesCollection = project(Projections.fields(Projections.excludeId(), Projections.include("name"), Projections.computed("borderingCountries", Projections.computed("$size", "$borders"))));

        int maxValue = collection.aggregate(Arrays.asList(borderingCountriesCollection, group(null, Accumulators.max("max", "$borderingCountries"))))
            .first()
            .getInteger("max");

        assertEquals(15, maxValue);

        Document maxNeighboredCountry = collection.aggregate(Arrays.asList(borderingCountriesCollection, match(Filters.eq("borderingCountries", maxValue))))
            .first();
        assertTrue(maxNeighboredCountry.containsValue("China"));

    }

    @AfterClass
    public static void cleanUp() {
        mongoClient.close();
    }

}
