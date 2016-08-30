package com.baeldung.elasticsearch;

import static org.elasticsearch.node.NodeBuilder.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class ElasticSearchUnitTests {
    private List<Person> listOfPersons = new ArrayList<Person>();
    String jsonString = null;

    @Before
    public void setUp() {
        Person person1 = new Person(10, "John Doe", new Date());
        Person person2 = new Person(25, "Janette Doe", new Date());
        listOfPersons.add(person1);
        listOfPersons.add(person2);
        jsonString = JSON.toJSONString(listOfPersons);
        System.out.println(jsonString);
    }

    @Test
    public void givenJsonString_whenJavaObject_thenIndexDocument() {
        String jsonObject = "{\"age\":20,\"dateOfBirth\":1471466076564,\"fullName\":\"John Doe\"}";

        Node node = nodeBuilder().clusterName("elasticsearch").client(true).node();
        Client client = node.client();
        IndexResponse response = client.prepareIndex("people", "Doe")
                .setSource(jsonObject).get();
        String id = response.getId();
        String index = response.getIndex();
        String type = response.getType();
        assertTrue(response.isCreated());
        assertEquals(index, "people");
        assertEquals(type, "Doe");
    }
    
    @Test
    public void givenDocumentId_whenJavaObject_thenDeleteDocument() {
        String jsonObject = "{\"age\":10,\"dateOfBirth\":1471455886564,\"fullName\":\"Johan Doe\"}";

        Node node = nodeBuilder().clusterName("elasticsearch").client(true).node();
        Client client = node.client();
        IndexResponse response = client.prepareIndex("people", "Doe")
                .setSource(jsonObject).get();
        String id = response.getId();
        DeleteResponse deleteResponse = client.prepareDelete("people", "Doe", id).get();
        assertTrue(deleteResponse.isFound());
    }
    
    @Test
    public void givenSearchRequest_whenMatchAll_thenReturnAllResults() {
        Node node = nodeBuilder().clusterName("elasticsearch").client(true).node();
        Client client = node.client();
        SearchResponse response = client.prepareSearch().execute().actionGet();
        SearchHit[] searchHits = response.getHits().getHits();
        List<Person> results = new ArrayList<Person>();
        for(SearchHit hit : searchHits){
            String sourceAsString = hit.getSourceAsString();
            Person person = JSON.parseObject(sourceAsString, Person.class);
            results.add(person);
        }
    }
    
    @Test
    public void givenSearchParamters_thenReturnResults() {
        Node node = nodeBuilder().clusterName("elasticsearch").client(true).node();
        Client client = node.client();
        SearchResponse response = client.prepareSearch()
                .setTypes()
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setPostFilter(QueryBuilders.rangeQuery("age").from(5).to(15))
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();
        SearchHit[] searchHits = response.getHits().getHits();
        List<Person> results = new ArrayList<Person>();
        for(SearchHit hit : searchHits){
            String sourceAsString = hit.getSourceAsString();
            Person person = JSON.parseObject(sourceAsString, Person.class);
            results.add(person);
        }
    }
    
    @Test
    public void givenContentBuilder_whenHelpers_thanIndexJson() throws IOException {
        Node node = nodeBuilder().clusterName("elasticsearch").client(true).node();
        Client client = node.client();
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                    .field("fullName", "Test")
                    .field("salary", "11500")
                    .field("age", "10")
                .endObject();
        IndexResponse response = client.prepareIndex("people", "Doe")
                .setSource(builder).get();
        assertTrue(response.isCreated());
    }
}
