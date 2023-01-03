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
        String case1 = PojoMethods.sampleApiRequest();
        String case2 = PojoMethods.sampleApiRequest();
        assertEquals(case1, case2);
    }

    @Test
    public void givenSampleApiAsyncCall_whenResponseIsMappedByJackson_thenCompareResponseMappedByJackson() throws Exception {
        String case1 = PojoMethods.asynJackson();
        String case2 = PojoMethods.asynJackson();
        assertEquals(case1, case2);
    }

    @Test
    public void givenSampleApiAsyncCall_whenResponseIsMappedByGson_thenCompareResponseMappedByGson() throws Exception {
        String case1 = PojoMethods.asynGson();
        String case2 = PojoMethods.asynGson();
        assertEquals(case1, case2);
    }

}
