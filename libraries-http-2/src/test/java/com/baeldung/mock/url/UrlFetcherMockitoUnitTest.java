package com.baeldung.mock.url;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class UrlFetcherMockitoUnitTest {
    
    @Test
    public void givenMockedUrl_whenRequestSent_thenIsUrlAvailableTrue() throws Exception {
        HttpURLConnection mockHttpURLConnection = mock(HttpURLConnection.class);
        when(mockHttpURLConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        
        URL mockURL = mock(URL.class);
        when(mockURL.openConnection()).thenReturn(mockHttpURLConnection);

        UrlFetcher fetcher = new UrlFetcher(mockURL);
        assertTrue("Url should be available: ", fetcher.isUrlAvailable());
    }

    @Test
    public void givenMockedUrl_whenRequestSent_thenIsUrlAvailableFalse() throws Exception {
        HttpURLConnection mockHttpURLConnection = mock(HttpURLConnection.class);
        when(mockHttpURLConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_NOT_FOUND);
        
        URL mockURL = mock(URL.class);
        when(mockURL.openConnection()).thenReturn(mockHttpURLConnection);
        
        UrlFetcher fetcher = new UrlFetcher(mockURL);
        assertFalse("Url should NOT be available: ", fetcher.isUrlAvailable());
    }

}
