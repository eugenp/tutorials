package com.baeldung.url;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import org.junit.Test;

public class UrlCheckerUnitTest {

    @Test
    public void givenValidUrl_WhenUsingHEAD_ThenReturn200() throws IOException {
        UrlChecker tester = new UrlChecker();
        int responseCode = tester.getResponseCodeForURLUsingHead("http://www.example.com");
        assertEquals(HttpURLConnection.HTTP_OK, responseCode);
    }
    
    @Test
    public void givenInvalidIUrl_WhenUsingHEAD_ThenReturn404() throws IOException {
        UrlChecker tester = new UrlChecker();
        int responseCode = tester.getResponseCodeForURLUsingHead("http://www.example.com/unkownurl");
        assertEquals(HttpURLConnection.HTTP_NOT_FOUND, responseCode);
    }
    
    @Test
    public void givenValidUrl_WhenUsingGET_ThenReturn200() throws IOException {
        UrlChecker tester = new UrlChecker();
        int responseCode = tester.getResponseCodeForURL("http://www.example.com");
        assertEquals(HttpURLConnection.HTTP_OK, responseCode);
    }
    
    @Test
    public void givenInvalidIUrl_WhenUsingGET_ThenReturn404() throws IOException {
        UrlChecker tester = new UrlChecker();
        int responseCode = tester.getResponseCodeForURL("http://www.example.com/unkownurl");
        assertEquals(HttpURLConnection.HTTP_NOT_FOUND, responseCode);
    }
    
}
