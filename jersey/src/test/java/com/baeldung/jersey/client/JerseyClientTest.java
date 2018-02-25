package com.baeldung.jersey.client;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

public class JerseyClientTest {

    private static int HTTP_OK = 200;
    
    @Test
    public void getHelloGreetingTest() {
        String response = JerseyClient.getHelloGreeting();
        
        Assert.assertEquals("hello", response);
    }
    
    @Test
    public void getHiGreetingTest() {
        String response = JerseyClient.getHiGreeting();
        
        Assert.assertEquals("hi", response);
    }
    
    @Test
    public void getCustomGreetingTest() {
        Response response = JerseyClient.getCustomGreeting();
        
        Assert.assertEquals(HTTP_OK, response.getStatus());
    }
    
}
