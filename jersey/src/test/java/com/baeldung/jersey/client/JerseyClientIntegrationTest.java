package com.baeldung.jersey.client;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class JerseyClientIntegrationTest {

    private static int HTTP_OK = 200;

    @Test
    public void givenGreetingResource_whenCallingHelloGreeting_thenHelloReturned() {
        String response = JerseyClient.getHelloGreeting();

        Assert.assertEquals("hello", response);
    }

    @Test
    public void givenGreetingResource_whenCallingHiGreeting_thenHiReturned() {
        String response = JerseyClient.getHiGreeting();

        Assert.assertEquals("hi", response);
    }

    @Test
    public void givenGreetingResource_whenCallingCustomGreeting_thenCustomGreetingReturned() {
        Response response = JerseyClient.getCustomGreeting();
        
        Assert.assertEquals(HTTP_OK, response.getStatus());
    }

}
