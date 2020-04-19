package com.baeldung.derive4j.pattern;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class HTTPRequestUnitTest {
    public static HTTPServer server;

    @BeforeClass
    public static void setUp() {
        server = new HTTPServer();
    }

    @Test
    public void givenHttpGETRequest_whenRequestReachesServer_thenProperResponseIsReturned() {
        HTTPRequest postRequest = HTTPRequests.POST("http://test.com/post", "Resource");
        HTTPResponse response = server.acceptRequest(postRequest);
        Assert.assertEquals(201, response.getStatusCode());
        Assert.assertEquals(HTTPServer.POST_RESPONSE_BODY, response.getResponseBody());
    }
}
