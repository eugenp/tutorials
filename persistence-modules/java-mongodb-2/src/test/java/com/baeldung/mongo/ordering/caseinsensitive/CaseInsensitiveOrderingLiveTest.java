package com.baeldung.mongo.ordering.caseinsensitive;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Sorts.ascending;
import static org.junit.Assert.assertEquals;

@Testcontainers
class CaseInsensitiveOrderingLiveTest {

    private static MongoCollection<Document> userCollections;

    @Container
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @BeforeAll
    private static void setup() {

        MongoClient mongoClient = new MongoClient(mongoDBContainer.getContainerIpAddress(), mongoDBContainer.getMappedPort(27017));
        MongoDatabase database = mongoClient.getDatabase("test");
        userCollections = database.getCollection("users");

        List<Document> list = new ArrayList<>();
        list.add(Document.parse("{'name': 'ben', surname: 'ThisField' }"));
        list.add(Document.parse("{'name': 'aen', surname: 'Does' }"));
        list.add(Document.parse("{'name': 'Ben', surname: 'Not' }"));
        list.add(Document.parse("{'name': 'cen', surname: 'Matter' }"));
        list.add(Document.parse("{'name': 'Aen', surname: 'Really' }"));
        list.add(Document.parse("{'name': 'Cen', surname: 'TrustMe' }"));

        userCollections.insertMany(list);
    }

    @Test
    void givenMongoCollection_whenUsingFindWithSort_caseIsConsideredByDefault() {
        FindIterable<Document> nameDoc = userCollections.find().sort(ascending("name"));
        MongoCursor<Document> cursor = nameDoc.cursor();

        List<String> expectedNamesOrdering = Arrays.asList("Aen", "Ben", "Cen", "aen", "ben", "cen");
        List<String> actualNamesOrdering = new ArrayList<>();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            actualNamesOrdering.add(document.get("name").toString());
        }

        assertEquals(expectedNamesOrdering, actualNamesOrdering);
    }

    @Test
    void givenMongoCollection_whenUsingFindWithSortAndCollation_caseIsNotConsidered() {
        FindIterable<Document> nameDoc = userCollections.find().sort(ascending("name"))
          .collation(Collation.builder().locale("en").build());
        MongoCursor<Document> cursor = nameDoc.cursor();
        List<String> expectedNamesOrdering = Arrays.asList("aen", "Aen", "ben", "Ben", "cen", "Cen");
        List<String> actualNamesOrdering = new ArrayList<>();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            actualNamesOrdering.add(document.get("name").toString());
        }

        assertEquals(expectedNamesOrdering, actualNamesOrdering);

    }

    @Test
    void givenMongoCollection_whenUsingFindWithSortAndAggregate_caseIsNotConsidered() {

        Bson projectBson = project(
          Projections.fields(
            Projections.include("name", "surname"),
            Projections.computed("lowerName", Projections.computed("$toLower", "$name"))));

        AggregateIterable<Document> nameDoc = userCollections.aggregate(
          Arrays.asList(projectBson,
            sort(Sorts.ascending("lowerName"))));

        MongoCursor<Document> cursor = nameDoc.cursor();

        List<String> expectedNamesOrdering = Arrays.asList("aen", "Aen", "ben", "Ben", "cen", "Cen");
        List<String> actualNamesOrdering = new ArrayList<>();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            actualNamesOrdering.add(document.get("name").toString());
        }

        assertEquals(expectedNamesOrdering, actualNamesOrdering);
    }


}
