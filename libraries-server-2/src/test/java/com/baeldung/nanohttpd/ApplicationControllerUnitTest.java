package com.baeldung.nanohttpd;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ApplicationControllerUnitTest {

    private static final String BASE_URL = "http://localhost:8072/";
    private static final HttpClient CLIENT = HttpClientBuilder.create().build();

    @BeforeClass
    public static void setUp() throws IOException {
        new ApplicationController();
    }

    @Test
    public void givenServer_whenRootRouteRequested_thenHelloWorldReturned() throws IOException {
        HttpResponse response = CLIENT.execute(new HttpGet(BASE_URL));
        assertTrue(IOUtils.toString(response.getEntity().getContent()).contains("Hello world!"));
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenServer_whenUsersRequested_thenThenAllUsersReturned() throws IOException {
        HttpResponse response = CLIENT.execute(new HttpGet(BASE_URL + "users"));
        assertEquals("UserA, UserB, UserC", IOUtils.toString(response.getEntity().getContent()));
    }
}