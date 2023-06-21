package com.baeldung.mock.url;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.Test;

class UrlFetcherMockitoUnitTest {
    
    @Test
    void givenMockedUrl_whenRequestSent_thenIsUrlAvailableTrue() throws Exception {
        HttpURLConnection mockHttpURLConnection = mock(HttpURLConnection.class);
        when(mockHttpURLConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        
        URL mockURL = mock(URL.class);
        when(mockURL.openConnection()).thenReturn(mockHttpURLConnection);

        UrlFetcher fetcher = new UrlFetcher(mockURL);
        assertTrue(fetcher.isUrlAvailable(), "Url should be available: ");
    }

    @Test
     void givenMockedUrl_whenRequestSent_thenIsUrlAvailableFalse() throws Exception {
        HttpURLConnection mockHttpURLConnection = mock(HttpURLConnection.class);
        when(mockHttpURLConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_NOT_FOUND);
        
        URL mockURL = mock(URL.class);
        when(mockURL.openConnection()).thenReturn(mockHttpURLConnection);
        
        UrlFetcher fetcher = new UrlFetcher(mockURL);
        assertFalse(fetcher.isUrlAvailable(), "Url should NOT be available: ");
    }

}
