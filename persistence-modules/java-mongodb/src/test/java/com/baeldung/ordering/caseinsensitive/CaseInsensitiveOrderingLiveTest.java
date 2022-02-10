package com.baeldung.ordering.caseinsensitive;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Sorts.ascending;

import static org.junit.Assert.*;

public class CaseInsensitiveOrderingLiveTest {

    private MongoClient mongoClient;
    private MongoCollection<Document> collection;

    @Before
    public void setup() {
        if (mongoClient == null) {
            mongoClient = new MongoClient();
            MongoDatabase db = mongoClient.getDatabase("ordering");
            collection = db.getCollection("users");

            List<Document> list = new ArrayList<>();

            list.add(Document.parse("{'name': 'ben', surname: 'ThisField' }"));
            list.add(Document.parse("{'name': 'aen', surname: 'Does' }"));
            list.add(Document.parse("{'name': 'Ben', surname: 'Not' }"));
            list.add(Document.parse("{'name': 'cen', surname: 'Matter' }"));
            list.add(Document.parse("{'name': 'Aen', surname: 'Really' }"));
            list.add(Document.parse("{'name': 'Cen', surname: 'TrustMe' }"));

            collection.insertMany(list);
        }
    }

    @After
    public void tearDown() {
        collection.deleteMany(Document.parse("{}"));
    }

    @Test
    public void givenMongoCollection_whenUsingFindWithSort_caseIsConsideredByDefault() {
        FindIterable<Document> nameDoc = collection.find().sort(ascending("name"));
        MongoCursor<Document> cursor = nameDoc.cursor();
        assertOrdering(cursor, Arrays.asList("Aen","Ben","Cen","aen","ben","cen"));
    }

    @Test
    public void givenMongoCollection_whenUsingFindWithSortAndCollation_caseIsNotConsidered() {
        FindIterable<Document> nameDoc = collection.find().sort(ascending("name"))
          .collation(Collation.builder().locale("en").build());
        MongoCursor<Document> cursor = nameDoc.cursor();
        assertOrdering(cursor, Arrays.asList("aen","Aen","ben","Ben","cen","Cen"));
    }

    @Test
    public void givenMongoCollection_whenUsingFindWithSortAndAggregate_caseIsNotConsidered() {

        Bson borderingCountriesCollection = project(
          Projections.fields(//Projections.excludeId(),
          Projections.include("name","surname"),
          Projections.computed("lowerName", Projections.computed("$toLower", "$name"))));

        AggregateIterable<Document> nameDoc = collection.aggregate(
          Arrays.asList(borderingCountriesCollection,
            sort(Sorts.ascending("lowerName"))));

        MongoCursor<Document> cursor = nameDoc.cursor();

        assertOrdering(cursor, Arrays.asList("aen","Aen","ben","Ben","cen","Cen"));
    }

    private void assertOrdering(MongoCursor<Document> cursor, List<String> expectedNamesOrdering) {
        List<String> actualNamesOrdering = new ArrayList<>();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            actualNamesOrdering.add(document.get("name").toString());
        }
        assertEquals(expectedNamesOrdering,actualNamesOrdering);
    }

}
