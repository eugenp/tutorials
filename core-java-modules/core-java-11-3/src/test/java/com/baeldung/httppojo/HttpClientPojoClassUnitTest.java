package com.baeldung.httppojo;

import static org.junit.Assert.*;

import org.junit.Test;

public class HttpClientPojoClassUnitTest {

    Todo expectedTodo = new Todo(1, 2, "quis ut nam facilis et officia qui", false);

    @Test
    public void givenSampleApiCall_whenResponseIsMappedByGson_thenCompareResponseMappedByGson() throws Exception {
        TodoAppClient sampleGson = new TodoAppClient();

        assertEquals(expectedTodo, sampleGson.syncGson());

    }

    @Test
    public void givenSampleApiCall_whenResponseIsMappedByJackson_thenCompareResponseMappedByJackson() throws Exception {
        TodoAppClient sampleJackson = new TodoAppClient();

        assertEquals(expectedTodo, sampleJackson.syncJackson());
    }

    @Test
    public void givenSampleRestApi_whenApiIsConsumedByHttpClient_thenCompareJsonString() throws Exception {
        TodoAppClient sampleTest = new TodoAppClient();
        assertNotNull(sampleTest.sampleApiRequest());

    }

    @Test
    public void givenSampleApiAsyncCall_whenResponseIsMappedByJackson_thenCompareResponseMappedByJackson() throws Exception {
        TodoAppClient sampleAsynJackson = new TodoAppClient();
        assertEquals(expectedTodo, sampleAsynJackson.asyncJackson());
    }

    @Test
    public void givenSampleApiAsyncCall_whenResponseIsMappedByGson_thenCompareResponseMappedByGson() throws Exception {
        TodoAppClient sampleAsynGson = new TodoAppClient();
        assertEquals(expectedTodo, sampleAsynGson.asyncGson());
    }

}
