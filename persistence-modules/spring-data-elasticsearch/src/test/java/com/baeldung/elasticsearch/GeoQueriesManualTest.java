package com.baeldung.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * This Manual test requires: Elasticsearch instance running on localhost:9200.
 * <p>
 * The following docker command can be used:
 * docker run -d --name elastic-test -p 9200:9200 -e "discovery.type=single-node" -e "xpack.security.enabled=false" docker.elastic.co/elasticsearch/elasticsearch:8.9.0
 */

@Slf4j
public class GeoQueriesManualTest {

    private static final String WONDERS_OF_WORLD = "wonders-of-world";

    private ElasticsearchClient client;

    @BeforeEach
    public void setUp() throws Exception {
        RestClient restClient = RestClient
                .builder(HttpHost.create("http://localhost:9200"))
                .build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        client = new ElasticsearchClient(transport);
        log.info("Creating index: {}", WONDERS_OF_WORLD);
        client.indices().create(builder -> builder.index(WONDERS_OF_WORLD)
                .mappings(bl -> bl.properties("name", name -> name.text(tx -> tx.index(false)))
                        .properties("region", region -> region.geoShape(gs -> gs))
                        .properties("location", location -> location.geoPoint(gp -> gp))));
    }

//    @Test
//    public void givenGeoShapeData_whenExecutedGeoShapeQuery_thenResultNonEmpty() throws IOException {
//        String jsonObject = "{\"name\":\"Agra\",\"region\":{\"type\":\"envelope\",\"coordinates\":[[75,30.2],[80.1, 25]]}}";
//        IndexRequest indexRequest = new IndexRequest(WONDERS_OF_WORLD);
//        indexRequest.source(jsonObject, XContentType.JSON);
//        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
//
//        String tajMahalId = response.getId();
//
//        RefreshRequest refreshRequest = new RefreshRequest(WONDERS_OF_WORLD);
//        client.indices()
//            .refresh(refreshRequest, RequestOptions.DEFAULT);
//
//        Coordinate topLeft = new Coordinate(74, 31.2);
//        Coordinate bottomRight = new Coordinate(81.1, 24);
//
//        GeoShapeQueryBuilder qb = QueryBuilders.geoShapeQuery("region", new EnvelopeBuilder(topLeft, bottomRight).buildGeometry());
//        qb.relation(ShapeRelation.INTERSECTS);
//
//        SearchSourceBuilder source = new SearchSourceBuilder().query(qb);
//        SearchRequest searchRequest = new SearchRequest(WONDERS_OF_WORLD);
//        searchRequest.source(source);
//
//        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//
//        List<String> ids = Arrays.stream(searchResponse.getHits()
//            .getHits())
//            .map(SearchHit::getId)
//            .collect(Collectors.toList());
//
//        assertTrue(ids.contains(tajMahalId));
//    }

    @Test
    public void givenGeoPointData_whenExecutedGeoBoundingBoxQuery_thenResultNonEmpty() throws Exception {
        Location pyramidsOfGiza = new Location("Pyramids of Giza", List.of(31.131302, 29.976480));
        IndexResponse response = client.index(builder -> builder
                .index(WONDERS_OF_WORLD)
                .id("1")
                .document(pyramidsOfGiza));

        String pyramidsOfGizaId = response.id();

        log.info("Indexed pyramid of Giza: {}", pyramidsOfGizaId);
        client.indices().refresh();

        SearchRequest.Builder builder = new SearchRequest.Builder().index(WONDERS_OF_WORLD);
        builder.query(bl1 -> bl1
                .geoBoundingBox(bl2 ->
                        bl2.field("location")
                                .boundingBox(bl3 -> bl3.tlbr(bl4 -> bl4
                                        .topLeft(bl -> bl.coords(List.of(30.0, 31.0)))
                                        .bottomRight(bl -> bl.coords(List.of(32.0, 28.0))))
                                )
                )
        );
        SearchRequest build = builder.build();
        SearchResponse<Location> searchResponse = client.search(build, Location.class);

        List<Location> returnedLocations = searchResponse.hits().hits().stream().map(Hit::source).toList();
        assertEquals(pyramidsOfGiza, returnedLocations.get(0));
    }
//
//    @Test
//    public void givenGeoPointData_whenExecutedGeoDistanceQuery_thenResultNonEmpty() throws Exception {
//        String jsonObject = "{\"name\":\"Lighthouse of alexandria\",\"location\":[31.131302,29.976480]}";
//
//        IndexRequest indexRequest = new IndexRequest(WONDERS_OF_WORLD);
//        indexRequest.source(jsonObject, XContentType.JSON);
//        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
//
//        String lighthouseOfAlexandriaId = response.getId();
//
//        RefreshRequest refreshRequest = new RefreshRequest(WONDERS_OF_WORLD);
//        client.indices()
//                .refresh(refreshRequest, RequestOptions.DEFAULT);
//
//        QueryBuilder qb = QueryBuilders.geoDistanceQuery("location")
//                .point(29.976, 31.131)
//                .distance(10, DistanceUnit.MILES);
//
//        SearchSourceBuilder source = new SearchSourceBuilder().query(qb);
//        SearchRequest searchRequest = new SearchRequest(WONDERS_OF_WORLD);
//        searchRequest.source(source);
//
//        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//
//        List<String> ids = Arrays.stream(searchResponse.getHits()
//                        .getHits())
//                .map(SearchHit::getId)
//                .collect(Collectors.toList());
//        assertTrue(ids.contains(lighthouseOfAlexandriaId));
//    }
//
//    @Test
//    public void givenGeoPointData_whenExecutedGeoPolygonQuery_thenResultNonEmpty() throws Exception {
//        String jsonObject = "{\"name\":\"The Great Rann of Kutch\",\"location\":[69.859741,23.733732]}";
//
//        IndexRequest indexRequest = new IndexRequest(WONDERS_OF_WORLD);
//        indexRequest.source(jsonObject, XContentType.JSON);
//        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
//
//        String greatRannOfKutchid = response.getId();
//
//        RefreshRequest refreshRequest = new RefreshRequest(WONDERS_OF_WORLD);
//        client.indices()
//                .refresh(refreshRequest, RequestOptions.DEFAULT);
//
//        List<GeoPoint> allPoints = new ArrayList<GeoPoint>();
//        allPoints.add(new GeoPoint(22.733, 68.859));
//        allPoints.add(new GeoPoint(24.733, 68.859));
//        allPoints.add(new GeoPoint(23, 70.859));
//        QueryBuilder qb = QueryBuilders.geoPolygonQuery("location", allPoints);
//
//        SearchSourceBuilder source = new SearchSourceBuilder().query(qb);
//        SearchRequest searchRequest = new SearchRequest(WONDERS_OF_WORLD);
//        searchRequest.source(source);
//
//        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//
//        List<String> ids = Arrays.stream(searchResponse.getHits()
//                        .getHits())
//                .map(SearchHit::getId)
//                .collect(Collectors.toList());
//        assertTrue(ids.contains(greatRannOfKutchid));
//    }

    @AfterEach
    public void destroy() throws Exception {
        client.indices().delete(builder -> builder.index(WONDERS_OF_WORLD));
    }
}
