package com.baeldung.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.SimpleQueryStringQuery;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * This Manual test requires: Elasticsearch instance running on localhost:9200.
 * <p>
 * The following docker command can be used:
 * docker run -d --name elastic-test -p 9200:9200 -e "discovery.type=single-node" -e "xpack.security.enabled=false" docker.elastic.co/elasticsearch/elasticsearch:8.9.0
 */
@Slf4j
@Disabled("Manual test")
public class ElasticSearchManualTest {
    private ElasticsearchClient client = null;

    @Before
    public void setUp() throws IOException {
        RestClient restClient = RestClient
                .builder(HttpHost.create("http://localhost:9200"))
                .build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        client = new ElasticsearchClient(transport);
        Person person1 = new Person(10, "John Doe", new Date());
        Person person2 = new Person(25, "Janette Doe", new Date());
        Person person3 = new Person(8, "Mark Doe", new Date());
        client.index(builder -> builder
                .index("person")
                .id(person1.getFullName())
                .document(person1));
        client.index(builder -> builder
                .index("person")
                .id(person2.getFullName())
                .document(person2));
        client.index(builder -> builder
                .index("person")
                .id(person3.getFullName())
                .document(person3));
    }

    @Test
    public void givenJsonDocument_whenJavaObject_thenIndexDocument() throws Exception {
        Person person = new Person(20, "Mark Doe", new Date(1471466076564L));
        IndexResponse response = client.index(i -> i
                .index("person")
                .id(person.getFullName())
                .document(person));

        log.info("Indexed with version: {}", response.version());
        assertEquals(Result.Created, response.result());
        assertEquals("person", response.index());
        assertEquals("Mark Doe", response.id());
    }

    @Test
    public void givenJsonString_whenJavaObject_thenIndexDocument() throws Exception {
        String jsonString = "{\"age\":10,\"dateOfBirth\":1471466076564,\"fullName\":\"John Doe\"}";
        StringReader stringReader = new StringReader(jsonString);
        IndexResponse response = client.index(i -> i
                .index("person")
                .id("John Doe")
                .withJson(stringReader));
        log.info("Indexed with version: {}", response.version());
        assertEquals("person", response.index());
        assertEquals("John Doe", response.id());
    }

    @Test
    public void givenDocumentId_whenJavaObject_thenDeleteDocument() throws Exception {
        String documentId = "Mark Doe";
        DeleteResponse response = client.delete(i -> i
                .index("person")
                .id(documentId));
        assertEquals(Result.Deleted, response.result());
        assertEquals("Mark Doe", response.id());
    }

    @Test
    public void givenSearchRequest_whenMatch_thenReturnAllResults() throws Exception {
        String searchText = "John";
        SearchResponse<Person> searchResponse = client.search(s -> s
                .index("person")
                .query(q -> q
                        .match(t -> t
                                .field("fullName")
                                .query(searchText))), Person.class);

        List<Hit<Person>> hits = searchResponse.hits().hits();
        assertEquals(1, hits.size());
        assertEquals("John Doe", hits.get(0).source().getFullName());
    }

    @Test
    public void givenGetRequest_whenMatch_thenReturnAllResults() throws IOException {
        String documentId = "John Doe";
        GetResponse<Person> getResponse = client.get(s -> s
                .index("person")
                .id(documentId), Person.class);
        Person source = getResponse.source();
        assertEquals("John Doe", source.getFullName());
    }

    @Test
    public void givenSearchRequest_whenMatchAndRange_thenReturnAllResults() throws Exception {
        String searchText = "John";
        SearchResponse<Person> searchResponse = client.search(s -> s
                        .index("person")
                        .query(q -> q
                                .match(t -> t
                                        .field("fullName").query(searchText)))
                        .query(q -> q
                                .range(range -> range
                                        .field("age").from("1").to("10"))),
                Person.class);

        List<Hit<Person>> hits = searchResponse.hits().hits();
        assertEquals(1, hits.size());
        assertEquals("John Doe", hits.get(0).source().getFullName());
    }


    @Test
    public void givenMultipleQueries_thenReturnResults() throws Exception {
        Query ageQuery = RangeQuery.of(r -> r.field("age").from("5").to("15"))._toQuery();
        SearchResponse<Person> response1 = client.search(s -> s.query(q -> q.bool(b -> b
                .must(ageQuery))), Person.class);
        response1.hits().hits().forEach(hit -> log.info("Response 1: {}", hit.source()));

        Query fullNameQuery = MatchQuery.of(m -> m.field("fullName").query("John"))._toQuery();
        SearchResponse<Person> response2 = client.search(s -> s.query(q -> q.bool(b -> b
                .must(fullNameQuery))), Person.class);
        response2.hits().hits().forEach(hit -> log.info("Response 2: {}", hit.source()));
        Query doeContainsQuery = SimpleQueryStringQuery.of(q -> q.query("*Doe"))._toQuery();
        SearchResponse<Person> response3 = client.search(s -> s.query(q -> q.bool(b -> b
                .must(doeContainsQuery))), Person.class);
        response3.hits().hits().forEach(hit -> log.info("Response 3: {}", hit.source()));

        Query simpleStringQuery = SimpleQueryStringQuery.of(q -> q.query("+John -Doe OR Janette"))._toQuery();
        SearchResponse<Person> response4 = client.search(s -> s.query(q -> q.bool(b -> b
                .must(simpleStringQuery))), Person.class);
        response4.hits().hits().forEach(hit -> log.info("Response 4: {}", hit.source()));

        SearchResponse<Person> response5 = client.search(s -> s.query(q -> q.bool(b -> b
                .must(ageQuery)
                .must(fullNameQuery)
                .must(simpleStringQuery))), Person.class);
        response5.hits().hits().forEach(hit -> log.info("Response 5: {}", hit.source()));
    }
}
