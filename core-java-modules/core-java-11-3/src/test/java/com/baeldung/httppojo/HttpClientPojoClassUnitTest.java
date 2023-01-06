package com.baeldung.httppojo;

import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.junit.Assert.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class HttpClientPojoClassUnitTest {

    @Test
    public void givenSampleApiCall_whenResponseIsMappedByGson_thenCompareResponseMappedByGson() throws Exception {
        PojoMethods sampleApi = new PojoMethods();
        String response = sampleApi.sampleApiRequest();

        Gson gson = new GsonBuilder().create();

        List<TodoApp> todoApp = gson.fromJson(response, new TypeToken<List<TodoApp>>() {
        }.getType());
        String title = todoApp.get(1)
            .getTitle()
            .toString();
        assertEquals("quis ut nam facilis et officia qui", title);

    }

    @Test
    public void givenSampleApiCall_whenResponseIsMappedByJackson_thenCompareResponseMappedByJackson() throws Exception {
        PojoMethods sampleApi = new PojoMethods();
        String response = sampleApi.sampleApiRequest();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        TodoApp[] app = objectMapper.treeToValue(jsonNode, TodoApp[].class);
        int one = app[1].getId();

        assertEquals(2, one);
    }

    @Test
    public void givenSampleRestApi_whenApiIsConsumedByHttpClient_thenCompareJsonString() throws Exception {
        PojoMethods sampleTest = new PojoMethods();
        assertNotNull(sampleTest.sampleApiRequest());

    }

    @Test
    public void givenSampleApiAsyncCall_whenResponseIsMappedByJackson_thenCompareResponseMappedByJackson() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://jsonplaceholder.typicode.com/todos"))
            .build();

        ObjectMappingJackson o = new ObjectMappingJackson();

        List<TodoApp> model = HttpClient.newHttpClient()
            .sendAsync(request, ofString())
            .thenApply(HttpResponse::body)
            .thenApply(o::readValue)
            .get();
        assertEquals(1, model.get(1)
            .getUserId());
    }

    @Test
    public void givenSampleApiAsyncCall_whenResponseIsMappedByGson_thenCompareResponseMappedByGson() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://jsonplaceholder.typicode.com/todos"))
            .build();
        ObjectMappingGson o = new ObjectMappingGson();

        List<TodoApp> model = HttpClient.newHttpClient()
            .sendAsync(request, ofString())
            .thenApply(HttpResponse::body)
            .thenApply(o::readValue)
            .get();
        assertEquals(false, model.get(1)
            .isCompleted());
    }

}
