package com.baeldung.elasticsearch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.GeoShapeRelation;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This Manual test requires: Elasticsearch instance running on localhost:9200.
 * <p>
 * The following docker command can be used: docker run -d --name elastic-test -p 9200:9200 -e
 * "discovery.type=single-node" -e "xpack.security.enabled=false"
 * docker.elastic.co/elasticsearch/elasticsearch:8.9.0
 */

@Slf4j
class GeoQueriesManualTest {

    private static final String WONDERS_OF_WORLD = "wonders-of-world";

    private ElasticsearchClient client;

    @BeforeEach
    public void setUp() throws Exception {
        RestClient restClient = RestClient.builder(HttpHost.create("http://localhost:9200"))
            .build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        client = new ElasticsearchClient(transport);
        log.info("Creating index: {}", WONDERS_OF_WORLD);
        client.indices()
            .create(builder -> builder.index(WONDERS_OF_WORLD)
                .mappings(typeMapping -> typeMapping.properties("region", region -> region.geoShape(gs -> gs))
                    .properties("location", location -> location.geoPoint(gp -> gp))));
    }

    @Test
    void givenGeoShapeData_whenExecutedGeoShapeQuery_thenResultNonEmpty() throws IOException {
        String jsonObject = """
            {
                "name":"Agra",
                "region":{
                    "type":"envelope",
                    "coordinates":[[75,30.2],[80.1,25]]
                }
            }
            """;
        IndexResponse response = client.index(idx -> idx.index(WONDERS_OF_WORLD)
            .withJson(new StringReader(jsonObject)));

        String tajMahalId = response.id();
        client.indices()
            .refresh();

        StringReader jsonData = new StringReader("""
            {
                 "type":"envelope",
                 "coordinates": [[74.0, 31.2], [81.1, 24.0 ] ]
            }
            """);

        SearchRequest searchRequest = new SearchRequest.Builder().query(query -> query.bool(boolQuery -> boolQuery.filter(query1 -> query1.geoShape(geoShapeQuery -> geoShapeQuery.field("region")
                .shape(geoShapeFieldQuery -> geoShapeFieldQuery.relation(GeoShapeRelation.Within)
                    .shape(JsonData.from(jsonData)))))))
            .build();
        log.info("Search request: {}", searchRequest);
        SearchResponse<Object> search = client.search(searchRequest, Object.class);
        log.info("Search response: {}", search);
        List<String> searchResults = search.hits()
            .hits()
            .stream()
            .map(Hit::id)
            .toList();
        assertTrue(searchResults.contains(tajMahalId));
    }

    @Test
    void givenGeoPointData_whenExecutedGeoBoundingBoxQuery_thenResultNonEmpty() throws Exception {
        Location pyramidsOfGiza = new Location("Pyramids of Giza", List.of(31.1328, 29.9761));
        IndexResponse response = client.index(builder -> builder.index(WONDERS_OF_WORLD)
            .document(pyramidsOfGiza));

        String pyramidsOfGizaId = response.id();

        log.info("Indexed pyramid of Giza: {}", pyramidsOfGizaId);
        client.indices()
            .refresh();

        SearchRequest.Builder builder = new SearchRequest.Builder().index(WONDERS_OF_WORLD);
        builder.query(query -> query.geoBoundingBox(geoBoundingBoxQuery -> geoBoundingBoxQuery.field("location")
            .boundingBox(geoBounds -> geoBounds.tlbr(bl4 -> bl4.topLeft(geoLocation -> geoLocation.coords(List.of(30.0, 31.0)))
                .bottomRight(geoLocation -> geoLocation.coords(List.of(32.0, 28.0)))))));
        SearchRequest build = builder.build();
        log.info("Search request: {}", build);
        SearchResponse<Location> searchResponse = client.search(build, Location.class);
        log.info("Search response: {}", searchResponse);
        List<Location> returnedLocations = searchResponse.hits()
            .hits()
            .stream()
            .map(Hit::source)
            .toList();
        assertEquals(pyramidsOfGiza, returnedLocations.get(0));
    }

    @Test
    void givenGeoPointData_whenExecutedGeoDistanceQuery_thenResultNonEmpty() throws Exception {
        String jsonObject = """
            {
                "name":"Lighthouse of alexandria",
                "location":{ "lat": 31.2139, "lon": 29.8856 }
            }
            """;
        IndexResponse response = client.index(idx -> idx.index(WONDERS_OF_WORLD)
            .withJson(new StringReader(jsonObject)));

        String lightHouseOfAlexandriaId = response.id();
        client.indices()
            .refresh();
        SearchRequest searchRequest = new SearchRequest.Builder().index(WONDERS_OF_WORLD)
            .query(query -> query.geoDistance(geoDistanceQuery -> geoDistanceQuery.field("location")
                .distance("10 miles")
                .location(geoLocation -> geoLocation.latlon(latLonGeoLocation -> latLonGeoLocation.lon(29.88)
                    .lat(31.21)))))
            .build();
        log.info("Search request: {}", searchRequest);
        SearchResponse<Object> search = client.search(searchRequest, Object.class);
        log.info("Search response: {}", search);
        List<String> ids = search.hits()
            .hits()
            .stream()
            .map(Hit::id)
            .toList();
        assertTrue(ids.contains(lightHouseOfAlexandriaId));
    }

    @Test
    void givenGeoPointData_whenExecutedGeoPolygonQuery_thenResultNonEmpty() throws Exception {
        String jsonObject = """
            {
                "name":"The Great Rann polygonPoints Kutch",
                "location":{"lon": 69.859741, "lat": 23.733732}
            }
            """;
        IndexResponse response = client.index(idx -> idx.index(WONDERS_OF_WORLD)
            .withJson(new StringReader(jsonObject)));
        String greatRannOfKutchid = response.id();
        client.indices()
            .refresh();
        log.info("Indexed greatRannOfKutchid: {}", greatRannOfKutchid);

        JsonData jsonData = JsonData.fromJson("""
            {
                "type":"polygon",
                "coordinates":[[[68.859,22.733],[68.859,24.733],[70.859,23]]]
            }
            """);

        SearchRequest build = new SearchRequest.Builder().query(query -> query.bool(boolQuery -> boolQuery.filter(query1 -> query1.geoShape(geoShapeQuery -> geoShapeQuery.field("location")
                .shape(geoShapeFieldQuery -> geoShapeFieldQuery.relation(GeoShapeRelation.Within)
                    .shape(jsonData))))))
            .build();
        log.info("Search request: {}", build);
        SearchResponse<Object> search = client.search(build, Object.class);
        log.info("Search response: {}", search);
        List<String> searchResults = search.hits()
            .hits()
            .stream()
            .map(Hit::id)
            .toList();
        assertTrue(searchResults.contains(greatRannOfKutchid));
    }

    @AfterEach
    public void destroy() throws Exception {
        client.indices()
            .delete(builder -> builder.index(WONDERS_OF_WORLD));
    }
}
