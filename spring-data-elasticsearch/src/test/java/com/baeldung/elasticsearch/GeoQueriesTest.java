package com.baeldung.elasticsearch;

import com.baeldung.spring.data.es.config.Config;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.geo.ShapeRelation;
import org.elasticsearch.common.geo.builders.ShapeBuilder;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class GeoQueriesTest {

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
        req.mapping(WONDERS, jsonObject);
        client.admin()
          .indices()
          .create(req)
          .actionGet();
    }

    @Test
    public void givenGeoShapeData_whenExecutedGeoShapeQuery_thenResultNonEmpty() {
        String jsonObject = "{\"name\":\"Agra\",\"region\":{\"type\":\"envelope\",\"coordinates\":[[75,25],[80.1,30.2]]}}";
        IndexResponse response = client.prepareIndex(WONDERS_OF_WORLD, WONDERS)
          .setSource(jsonObject)
          .get();
        String tajMahalId = response.getId();
        client.admin()
          .indices()
          .prepareRefresh(WONDERS_OF_WORLD)
          .get();

        QueryBuilder qb = QueryBuilders.geoShapeQuery("region", ShapeBuilder.newEnvelope()
          .topLeft(74.00, 24.0)
          .bottomRight(81.1, 31.2))
          .relation(ShapeRelation.WITHIN);

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
          .setSource(jsonObject)
          .get();
        String pyramidsOfGizaId = response.getId();
        client.admin()
          .indices()
          .prepareRefresh(WONDERS_OF_WORLD)
          .get();

        QueryBuilder qb = QueryBuilders.geoBoundingBoxQuery("location")
          .bottomLeft(28, 30)
          .topRight(31, 32);

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
          .setSource(jsonObject)
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
          .setSource(jsonObject)
          .get();
        String greatRannOfKutchid = response.getId();
        client.admin()
          .indices()
          .prepareRefresh(WONDERS_OF_WORLD)
          .get();

        QueryBuilder qb = QueryBuilders.geoPolygonQuery("location")
          .addPoint(22.733, 68.859)
          .addPoint(24.733, 68.859)
          .addPoint(23, 70.859);

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
