package com.baeldung.nanohttpd;

import static org.junit.Assert.*;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class ItemGetControllerUnitTest {

    private static final String URL = "http://localhost:8071";
    private static final HttpClient CLIENT = HttpClientBuilder.create().build();

    @BeforeClass
    public static void setUp() throws IOException {
        new ItemGetController();
    }

    @Test
    public void givenServer_whenDoingGet_thenParamIsReadCorrectly() throws IOException {
        HttpResponse response = CLIENT.execute(new HttpGet(URL + "?itemId=1234"));
        assertEquals("Requested itemId = 1234", IOUtils.toString(response.getEntity().getContent()));
    }

    @Test
    public void givenServer_whenDoingPost_then404IsReturned() throws IOException {
        HttpResponse response = CLIENT.execute(new HttpPost(URL));
        assertEquals(404, response.getStatusLine().getStatusCode());
    }
}