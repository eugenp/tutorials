package com.baeldung.mongo.geo;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Polygon;
import com.mongodb.client.model.geojson.Position;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MongoGeospatialLiveTest {

    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection<Document> collection;

    @Before
    public void setup() {
        if (mongoClient == null) {
            mongoClient = new MongoClient();
            db = mongoClient.getDatabase("myMongoDb");
            collection = db.getCollection("places");
            collection.deleteMany(new Document());
            collection.createIndex(Indexes.geo2dsphere("location"));
            collection.insertOne(Document.parse("{'name':'Big Ben','location': {'coordinates':[-0.1268194,51.5007292],'type':'Point'}}"));
            collection.insertOne(Document.parse("{'name':'Hyde Park','location': {'coordinates': [[[-0.159381,51.513126],[-0.189615,51.509928],[-0.187373,51.502442], [-0.153019,51.503464],[-0.159381,51.513126]]],'type':'Polygon'}}"));
        }
    }

    @Test
    public void givenNearbyLocation_whenSearchNearby_thenFound() {
        Point currentLoc = new Point(new Position(-0.126821, 51.495885));
        FindIterable<Document> result = collection.find(Filters.near("location", currentLoc, 1000.0, 10.0));

        assertNotNull(result.first());
        assertEquals("Big Ben", result.first().get("name"));
    }

    @Test
    public void givenFarLocation_whenSearchNearby_thenNotFound() {
        Point currentLoc = new Point(new Position(-0.5243333, 51.4700223));
        FindIterable<Document> result = collection.find(Filters.near("location", currentLoc, 5000.0, 10.0));

        assertNull(result.first());
    }

    @Test
    public void givenNearbyLocation_whenSearchWithinCircleSphere_thenFound() {
        double distanceInRad = 5.0 / 6371;
        FindIterable<Document> result = collection.find(Filters.geoWithinCenterSphere("location", -0.1435083, 51.4990956, distanceInRad));

        assertNotNull(result.first());
        assertEquals("Big Ben", result.first().get("name"));
    }

    @Test
    public void givenNearbyLocation_whenSearchWithinBox_thenFound() {
        double lowerLeftX = -0.1427638;
        double lowerLeftY = 51.4991288;
        double upperRightX = -0.1256209;
        double upperRightY = 51.5030272;

        FindIterable<Document> result = collection.find(Filters.geoWithinBox("location", lowerLeftX, lowerLeftY, upperRightX, upperRightY));

        assertNotNull(result.first());
        assertEquals("Big Ben", result.first().get("name"));
    }

    @Test
    public void givenNearbyLocation_whenSearchWithinPolygon_thenFound() {
        ArrayList<List<Double>> points = new ArrayList<List<Double>>();
        points.add(Arrays.asList(-0.1439, 51.4952)); // victoria station
        points.add(Arrays.asList(-0.1121, 51.4989));// Lambeth North
        points.add(Arrays.asList(-0.13, 51.5163));// Tottenham Court Road
        points.add(Arrays.asList(-0.1439, 51.4952)); // victoria station
        FindIterable<Document> result = collection.find(Filters.geoWithinPolygon("location", points));

        assertNotNull(result.first());
        assertEquals("Big Ben", result.first().get("name"));
    }

    @Test
    public void givenNearbyLocation_whenSearchUsingIntersect_thenFound() {
        ArrayList<Position> positions = new ArrayList<Position>();
        positions.add(new Position(-0.1439, 51.4952));
        positions.add(new Position(-0.1346, 51.4978));
        positions.add(new Position(-0.2177, 51.5135));
        positions.add(new Position(-0.1439, 51.4952));
        Polygon geometry = new Polygon(positions);
        FindIterable<Document> result = collection.find(Filters.geoIntersects("location", geometry));

        assertNotNull(result.first());
        assertEquals("Hyde Park", result.first().get("name"));
    }

}
