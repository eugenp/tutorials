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

    // String [] expectedResponse = {userId=1, id=2, title};
    // String jsonString = "{userId=1, id=2, title='quis ut nam facilis et officia qui', completed=false}";
    TodoApp expectedTodo = new TodoApp(1, 2, "quis ut nam facilis et officia qui", false);

    @Test
    public void givenSampleApiCall_whenResponseIsMappedByGson_thenCompareResponseMappedByGson() throws Exception {
        PojoMethods sampleGson = new PojoMethods();

        assertEquals(expectedTodo, sampleGson.gsonMethod());

    }

    @Test
    public void givenSampleApiCall_whenResponseIsMappedByJackson_thenCompareResponseMappedByJackson() throws Exception {
        PojoMethods sampleJackson = new PojoMethods();

        assertEquals(expectedTodo, sampleJackson.jacksonRe());
    }

    @Test
    public void givenSampleRestApi_whenApiIsConsumedByHttpClient_thenCompareJsonString() throws Exception {
        PojoMethods sampleTest = new PojoMethods();
        assertNotNull(sampleTest.sampleApiRequest());

    }

    @Test
    public void givenSampleApiAsyncCall_whenResponseIsMappedByJackson_thenCompareResponseMappedByJackson() throws Exception {
        PojoMethods sampleAsynJackson = new PojoMethods();
        assertEquals(expectedTodo, sampleAsynJackson.asynJackson());
    }

    @Test
    public void givenSampleApiAsyncCall_whenResponseIsMappedByGson_thenCompareResponseMappedByGson() throws Exception {
        PojoMethods sampleAsynGson = new PojoMethods();
        assertEquals(expectedTodo, sampleAsynGson.asynGson());
    }

}
