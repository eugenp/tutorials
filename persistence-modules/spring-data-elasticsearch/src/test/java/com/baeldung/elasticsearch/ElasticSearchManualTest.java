package com.baeldung.elasticsearch;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSON;

import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * This Manual test requires: * Elasticsearch instance running on localhost:9200.
 * The following docker command can be used:
 * docker run -d --name es761 -p 9200:9200 -e "discovery.type=single-node" elasticsearch:7.6.1
 */
public class ElasticSearchManualTest {
  private List<Person> listOfPersons = new ArrayList<>();
  private RestHighLevelClient client = null;

  @Before
  public void setUp() throws UnknownHostException {
    Person person1 = new Person(10, "John Doe", new Date());
    Person person2 = new Person(25, "Janette Doe", new Date());
    listOfPersons.add(person1);
    listOfPersons.add(person2);

    ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("localhost:9200").build();
    client = RestClients.create(clientConfiguration).rest();
  }

  @Test
  public void givenJsonString_whenJavaObject_thenIndexDocument() throws Exception {
    String jsonObject = "{\"age\":20,\"dateOfBirth\":1471466076564,\"fullName\":\"John Doe\"}";
    IndexRequest request = new IndexRequest("people");
    request.source(jsonObject, XContentType.JSON);
    
    IndexResponse response = client.index(request, RequestOptions.DEFAULT);
    String index = response.getIndex();
    long version = response.getVersion();
    
    assertEquals(Result.CREATED, response.getResult());
    assertEquals(1, version);
    assertEquals("people", index);
  }

  @Test
  public void givenDocumentId_whenJavaObject_thenDeleteDocument() throws Exception {
    String jsonObject = "{\"age\":10,\"dateOfBirth\":1471455886564,\"fullName\":\"Johan Doe\"}";
    IndexRequest indexRequest = new IndexRequest("people");
    indexRequest.source(jsonObject, XContentType.JSON);

    IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
    String id = response.getId();

    GetRequest getRequest = new GetRequest("people");
    getRequest.id(id);

    GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
    System.out.println(getResponse.getSourceAsString());

    DeleteRequest deleteRequest = new DeleteRequest("people");
    deleteRequest.id(id);

    DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);

    assertEquals(Result.DELETED, deleteResponse.getResult());
  }

  @Test
  public void givenSearchRequest_whenMatchAll_thenReturnAllResults() throws Exception {
    SearchRequest searchRequest = new SearchRequest();
    SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
    SearchHit[] searchHits = response.getHits().getHits();
    List<Person> results = Arrays.stream(searchHits).map(hit -> JSON.parseObject(hit.getSourceAsString(), Person.class))
        .collect(Collectors.toList());

    results.forEach(System.out::println);
  }

  @Test
  public void givenSearchParameters_thenReturnResults() throws Exception {
    SearchSourceBuilder builder = 
      new SearchSourceBuilder().postFilter(QueryBuilders.rangeQuery("age").from(5).to(15));

    SearchRequest searchRequest = new SearchRequest();
    searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
    searchRequest.source(builder);

    SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

    builder = new SearchSourceBuilder().postFilter(QueryBuilders.simpleQueryStringQuery("+John -Doe OR Janette"));

    searchRequest = new SearchRequest();
    searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
    searchRequest.source(builder);

    SearchResponse response2 = client.search(searchRequest, RequestOptions.DEFAULT);

    builder = new SearchSourceBuilder().postFilter(QueryBuilders.matchQuery("John", "Name*"));
    searchRequest = new SearchRequest();
    searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
    searchRequest.source(builder);

    SearchResponse response3 = client.search(searchRequest, RequestOptions.DEFAULT);

    response2.getHits();
    response3.getHits();
    
    final List<Person> results = 
      Stream.of(response.getHits().getHits(), 
        response2.getHits().getHits(), 
        response3.getHits().getHits())
      .flatMap(Arrays::stream)
      .map(hit -> JSON.parseObject(hit.getSourceAsString(), Person.class))
      .collect(Collectors.toList());
    
    results.forEach(System.out::println);
  }

  @Test
  public void givenContentBuilder_whenHelpers_thanIndexJson() throws IOException {
    XContentBuilder builder = XContentFactory.jsonBuilder().startObject().field("fullName", "Test")
        .field("salary", "11500").field("age", "10").endObject();

    IndexRequest indexRequest = new IndexRequest("people");
    indexRequest.source(builder);

    IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

    assertEquals(Result.CREATED, response.getResult());
  }
}
