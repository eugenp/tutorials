package com.baeldung.elasticsearch;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.geo.builders.EnvelopeBuilder;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.spring.data.es.config.Config;

/**
 * 
 * This Manual test requires:
 * * Elasticsearch instance running on host
 * * with cluster name = elasticsearch
 * * and further configurations
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class GeoQueriesManualTest {

    private static final String WONDERS_OF_WORLD = "wonders-of-world";
    private static final String WONDERS = "Wonders";

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private Client client;

    @Before
    public void setUp() {
        String jsonObject = "{\"Wonders\":{\"properties\":{\"name\":{\"type\":\"string\",\"index\":\"not_analyzed\"},\"region\":{\"type\":\"geo_shape\",\"tree\":\"quadtree\",\"precision\":\"1m\"},\"location\":{\"type\":\"geo_point\"}}}}";
        CreateIndexRequest req = new CreateIndexRequest(WONDERS_OF_WORLD);
        req.mapping(WONDERS, jsonObject, XContentType.JSON);
        client.admin()
          .indices()
          .create(req)
          .actionGet();
    }

    @Test
    public void givenGeoShapeData_whenExecutedGeoShapeQuery_thenResultNonEmpty() throws IOException{
        String jsonObject = "{\"name\":\"Agra\",\"region\":{\"type\":\"envelope\",\"coordinates\":[[75,30.2],[80.1, 25]]}}";
        IndexResponse response = client.prepareIndex(WONDERS_OF_WORLD, WONDERS)
          .setSource(jsonObject, XContentType.JSON)
          .get();
        
        String tajMahalId = response.getId();
        client.admin()
          .indices()
          .prepareRefresh(WONDERS_OF_WORLD)
          .get();
 
        Coordinate topLeft = new Coordinate(74, 31.2);
        Coordinate bottomRight = new Coordinate(81.1, 24);
        QueryBuilder qb = QueryBuilders
            .geoShapeQuery("region", new EnvelopeBuilder(topLeft, bottomRight));

        SearchResponse searchResponse = client.prepareSearch(WONDERS_OF_WORLD)
          .setTypes(WONDERS)
          .setQuery(qb)
          .execute()
          .actionGet();

        List<String> ids = Arrays.stream(searchResponse.getHits()
          .getHits())
          .map(SearchHit::getId)
          .collect(Collectors.toList());

        assertTrue(ids.contains(tajMahalId));
    }

    @Test
    public void givenGeoPointData_whenExecutedGeoBoundingBoxQuery_thenResultNonEmpty() {
        String jsonObject = "{\"name\":\"Pyramids of Giza\",\"location\":[31.131302,29.976480]}";
        IndexResponse response = client.prepareIndex(WONDERS_OF_WORLD, WONDERS)
          .setSource(jsonObject, XContentType.JSON)
          .get();
        String pyramidsOfGizaId = response.getId();
        client.admin()
          .indices()
          .prepareRefresh(WONDERS_OF_WORLD)
          .get();

        QueryBuilder qb = QueryBuilders.geoBoundingBoxQuery("location")
            .setCorners(31,30,28,32);
        
        SearchResponse searchResponse = client.prepareSearch(WONDERS_OF_WORLD)
          .setTypes(WONDERS)
          .setQuery(qb)
          .execute()
          .actionGet();
        List<String> ids = Arrays.stream(searchResponse.getHits()
          .getHits())
          .map(SearchHit::getId)
          .collect(Collectors.toList());
        assertTrue(ids.contains(pyramidsOfGizaId));
    }

    @Test
    public void givenGeoPointData_whenExecutedGeoDistanceQuery_thenResultNonEmpty() {
        String jsonObject = "{\"name\":\"Lighthouse of alexandria\",\"location\":[31.131302,29.976480]}";
        IndexResponse response = client.prepareIndex(WONDERS_OF_WORLD, WONDERS)
          .setSource(jsonObject, XContentType.JSON)
          .get();
        String lighthouseOfAlexandriaId = response.getId();
        client.admin()
          .indices()
          .prepareRefresh(WONDERS_OF_WORLD)
          .get();

        QueryBuilder qb = QueryBuilders.geoDistanceQuery("location")
          .point(29.976, 31.131)
          .distance(10, DistanceUnit.MILES);

        SearchResponse searchResponse = client.prepareSearch(WONDERS_OF_WORLD)
          .setTypes(WONDERS)
          .setQuery(qb)
          .execute()
          .actionGet();
        List<String> ids = Arrays.stream(searchResponse.getHits()
          .getHits())
          .map(SearchHit::getId)
          .collect(Collectors.toList());
        assertTrue(ids.contains(lighthouseOfAlexandriaId));
    }

    @Test
    public void givenGeoPointData_whenExecutedGeoPolygonQuery_thenResultNonEmpty() {
        String jsonObject = "{\"name\":\"The Great Rann of Kutch\",\"location\":[69.859741,23.733732]}";
        IndexResponse response = client.prepareIndex(WONDERS_OF_WORLD, WONDERS)
          .setSource(jsonObject, XContentType.JSON)
          .get();
        String greatRannOfKutchid = response.getId();
        client.admin()
          .indices()
          .prepareRefresh(WONDERS_OF_WORLD)
          .get();

        List<GeoPoint> allPoints = new ArrayList<GeoPoint>();
        allPoints.add(new GeoPoint(22.733, 68.859));
        allPoints.add(new GeoPoint(24.733, 68.859));
        allPoints.add(new GeoPoint(23, 70.859));
        QueryBuilder qb = QueryBuilders.geoPolygonQuery("location", allPoints);

        SearchResponse searchResponse = client.prepareSearch(WONDERS_OF_WORLD)
          .setTypes(WONDERS)
          .setQuery(qb)
          .execute()
          .actionGet();
        List<String> ids = Arrays.stream(searchResponse.getHits()
          .getHits())
          .map(SearchHit::getId)
          .collect(Collectors.toList());
        assertTrue(ids.contains(greatRannOfKutchid));
    }

    @After
    public void destroy() {
        elasticsearchTemplate.deleteIndex(WONDERS_OF_WORLD);
    }
}
