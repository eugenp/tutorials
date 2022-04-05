package com.baeldung.url;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class UrlCheckerUnitTest {

    @Test
    public void givenValidUrl_WhenUsingHEAD_ThenReturn200() throws IOException {
        UrlChecker tester = new UrlChecker();
        int responseCode = tester.getResponseCodeForURLUsingHead("http://www.example.com");
        assertEquals(200, responseCode);
    }
    
    @Test
    public void givenInvalidIUrl_WhenUsingHEAD_ThenReturn404() throws IOException {
        UrlChecker tester = new UrlChecker();
        int responseCode = tester.getResponseCodeForURLUsingHead("http://www.example.com/unkownurl");
        assertEquals(404, responseCode);
    }
    
    @Test
    public void givenValidUrl_WhenUsingGET_ThenReturn200() throws IOException {
        UrlChecker tester = new UrlChecker();
        int responseCode = tester.getResponseCodeForURL("http://www.example.com");
        assertEquals(200, responseCode);
    }
    
    @Test
    public void givenInvalidIUrl_WhenUsingGET_ThenReturn404() throws IOException {
        UrlChecker tester = new UrlChecker();
        int responseCode = tester.getResponseCodeForURL("http://www.example.com/unkownurl");
        assertEquals(404, responseCode);
    }
    
}
