package com.baeldung.elasticsearch;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.baeldung.spring.data.es.config.Config;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.geo.ShapeRelation;
import org.elasticsearch.common.geo.builders.EnvelopeBuilder;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.GeoShapeQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This Manual test requires: Elasticsearch instance running on localhost:9200.
 * 
 * The following docker command can be used: docker run -d --name es761 -p
 * 9200:9200 -e "discovery.type=single-node" elasticsearch:7.6.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class GeoQueriesManualTest {

    private static final String WONDERS_OF_WORLD = "wonders-of-world";

    @Autowired
    private RestHighLevelClient client;

    @Before
    public void setUp() throws Exception {
        String jsonObject = "{\"properties\":{\"name\":{\"type\":\"text\",\"index\":false},\"region\":{\"type\":\"geo_shape\"},\"location\":{\"type\":\"geo_point\"}}}";

        CreateIndexRequest req = new CreateIndexRequest(WONDERS_OF_WORLD);
        req.mapping(jsonObject, XContentType.JSON);

        client.indices()
            .create(req, RequestOptions.DEFAULT);
    }

    @Test
    public void givenGeoShapeData_whenExecutedGeoShapeQuery_thenResultNonEmpty() throws IOException {
        String jsonObject = "{\"name\":\"Agra\",\"region\":{\"type\":\"envelope\",\"coordinates\":[[75,30.2],[80.1, 25]]}}";
        IndexRequest indexRequest = new IndexRequest(WONDERS_OF_WORLD);
        indexRequest.source(jsonObject, XContentType.JSON);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

        String tajMahalId = response.getId();

        RefreshRequest refreshRequest = new RefreshRequest(WONDERS_OF_WORLD);
        client.indices()
            .refresh(refreshRequest, RequestOptions.DEFAULT);

        Coordinate topLeft = new Coordinate(74, 31.2);
        Coordinate bottomRight = new Coordinate(81.1, 24);

        GeoShapeQueryBuilder qb = QueryBuilders.geoShapeQuery("region", new EnvelopeBuilder(topLeft, bottomRight).buildGeometry());
        qb.relation(ShapeRelation.INTERSECTS);

        SearchSourceBuilder source = new SearchSourceBuilder().query(qb);
        SearchRequest searchRequest = new SearchRequest(WONDERS_OF_WORLD);
        searchRequest.source(source);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        List<String> ids = Arrays.stream(searchResponse.getHits()
            .getHits())
            .map(SearchHit::getId)
            .collect(Collectors.toList());

        assertTrue(ids.contains(tajMahalId));
    }

    @Test
    public void givenGeoPointData_whenExecutedGeoBoundingBoxQuery_thenResultNonEmpty() throws Exception {
        String jsonObject = "{\"name\":\"Pyramids of Giza\",\"location\":[31.131302,29.976480]}";

        IndexRequest indexRequest = new IndexRequest(WONDERS_OF_WORLD);
        indexRequest.source(jsonObject, XContentType.JSON);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

        String pyramidsOfGizaId = response.getId();

        RefreshRequest refreshRequest = new RefreshRequest(WONDERS_OF_WORLD);
        client.indices()
            .refresh(refreshRequest, RequestOptions.DEFAULT);

        QueryBuilder qb = QueryBuilders.geoBoundingBoxQuery("location")
            .setCorners(31, 30, 28, 32);

        SearchSourceBuilder source = new SearchSourceBuilder().query(qb);
        SearchRequest searchRequest = new SearchRequest(WONDERS_OF_WORLD);
        searchRequest.source(source);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        List<String> ids = Arrays.stream(searchResponse.getHits()
            .getHits())
            .map(SearchHit::getId)
            .collect(Collectors.toList());
        assertTrue(ids.contains(pyramidsOfGizaId));
    }

    @Test
    public void givenGeoPointData_whenExecutedGeoDistanceQuery_thenResultNonEmpty() throws Exception {
        String jsonObject = "{\"name\":\"Lighthouse of alexandria\",\"location\":[31.131302,29.976480]}";

        IndexRequest indexRequest = new IndexRequest(WONDERS_OF_WORLD);
        indexRequest.source(jsonObject, XContentType.JSON);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

        String lighthouseOfAlexandriaId = response.getId();

        RefreshRequest refreshRequest = new RefreshRequest(WONDERS_OF_WORLD);
        client.indices()
            .refresh(refreshRequest, RequestOptions.DEFAULT);

        QueryBuilder qb = QueryBuilders.geoDistanceQuery("location")
            .point(29.976, 31.131)
            .distance(10, DistanceUnit.MILES);

        SearchSourceBuilder source = new SearchSourceBuilder().query(qb);
        SearchRequest searchRequest = new SearchRequest(WONDERS_OF_WORLD);
        searchRequest.source(source);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        List<String> ids = Arrays.stream(searchResponse.getHits()
            .getHits())
            .map(SearchHit::getId)
            .collect(Collectors.toList());
        assertTrue(ids.contains(lighthouseOfAlexandriaId));
    }

    @Test
    public void givenGeoPointData_whenExecutedGeoPolygonQuery_thenResultNonEmpty() throws Exception {
        String jsonObject = "{\"name\":\"The Great Rann of Kutch\",\"location\":[69.859741,23.733732]}";

        IndexRequest indexRequest = new IndexRequest(WONDERS_OF_WORLD);
        indexRequest.source(jsonObject, XContentType.JSON);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

        String greatRannOfKutchid = response.getId();

        RefreshRequest refreshRequest = new RefreshRequest(WONDERS_OF_WORLD);
        client.indices()
            .refresh(refreshRequest, RequestOptions.DEFAULT);

        List<GeoPoint> allPoints = new ArrayList<GeoPoint>();
        allPoints.add(new GeoPoint(22.733, 68.859));
        allPoints.add(new GeoPoint(24.733, 68.859));
        allPoints.add(new GeoPoint(23, 70.859));
        QueryBuilder qb = QueryBuilders.geoPolygonQuery("location", allPoints);

        SearchSourceBuilder source = new SearchSourceBuilder().query(qb);
        SearchRequest searchRequest = new SearchRequest(WONDERS_OF_WORLD);
        searchRequest.source(source);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        List<String> ids = Arrays.stream(searchResponse.getHits()
            .getHits())
            .map(SearchHit::getId)
            .collect(Collectors.toList());
        assertTrue(ids.contains(greatRannOfKutchid));
    }

    @After
    public void destroy() throws Exception {
        DeleteIndexRequest deleteIndex = new DeleteIndexRequest(WONDERS_OF_WORLD);
        client.indices()
            .delete(deleteIndex, RequestOptions.DEFAULT);
    }
}
