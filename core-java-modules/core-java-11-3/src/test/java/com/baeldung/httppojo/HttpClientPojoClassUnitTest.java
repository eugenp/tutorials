package com.baeldung.httppojo;

import static org.junit.Assert.*;

import org.junit.Test;

public class HttpClientPojoClassUnitTest {

    TodoApp expectedTodo = new TodoApp(1, 2, "quis ut nam facilis et officia qui", false);

    @Test
    public void givenSampleApiCall_whenResponseIsMappedByGson_thenCompareResponseMappedByGson() throws Exception {
        TodoAppLogic sampleGson = new TodoAppLogic();

        assertEquals(expectedTodo, sampleGson.syncGson());

    }

    @Test
    public void givenSampleApiCall_whenResponseIsMappedByJackson_thenCompareResponseMappedByJackson() throws Exception {
        TodoAppLogic sampleJackson = new TodoAppLogic();

        assertEquals(expectedTodo, sampleJackson.syncJackson());
    }

    @Test
    public void givenSampleRestApi_whenApiIsConsumedByHttpClient_thenCompareJsonString() throws Exception {
        TodoAppLogic sampleTest = new TodoAppLogic();
        assertNotNull(sampleTest.sampleApiRequest());

    }

    @Test
    public void givenSampleApiAsyncCall_whenResponseIsMappedByJackson_thenCompareResponseMappedByJackson() throws Exception {
        TodoAppLogic sampleAsynJackson = new TodoAppLogic();
        assertEquals(expectedTodo, sampleAsynJackson.asyncJackson());
    }

    @Test
    public void givenSampleApiAsyncCall_whenResponseIsMappedByGson_thenCompareResponseMappedByGson() throws Exception {
        TodoAppLogic sampleAsynGson = new TodoAppLogic();
        assertEquals(expectedTodo, sampleAsynGson.asyncGson());
    }

}
