package com.baeldung.httppojo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class HttpClientPojoClassUnitTest {

    @Test
    public void givenSampleApiCall_whenResponseIsMappedByGson_thenCompareResponseMappedByGson() throws Exception {
        PojoMethods gsonTest = new PojoMethods();
        String case1 = gsonTest.gsonMethod();
        String case2 = gsonTest.gsonMethod();
        assertEquals(case1, case2);
    }

    @Test
    public void givenSampleApiCall_whenResponseIsMappedByJackson_thenCompareResponseMappedByJackson() throws Exception {
        PojoMethods jacksonTest = new PojoMethods();
        String case1 = jacksonTest.jacksonRe();
        String case2 = jacksonTest.jacksonRe();
        assertEquals(case1, case2);
    }

    @Test
    public void givenSampleRestApi_whenApiIsConsumedByHttpClient_thenCompareJsonString() throws Exception {
        PojoMethods sampleTest = new PojoMethods();
        String case1 = sampleTest.sampleApiRequest();
        String case2 = sampleTest.sampleApiRequest();
        assertEquals(case1, case2);
    }

    @Test
    public void givenSampleApiAsyncCall_whenResponseIsMappedByJackson_thenCompareResponseMappedByJackson() throws Exception {
        PojoMethods jacksonTest = new PojoMethods();
        String case1 = jacksonTest.asynJackson();
        String case2 = jacksonTest.asynJackson();
        assertEquals(case1, case2);
    }

    @Test
    public void givenSampleApiAsyncCall_whenResponseIsMappedByGson_thenCompareResponseMappedByGson() throws Exception {
        PojoMethods gsonTest = new PojoMethods();
        String case1 = gsonTest.asynGson();
        String case2 = gsonTest.asynGson();
        assertEquals(case1, case2);
    }

}
