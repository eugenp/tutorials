package com.baeldung.mock.url;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UrlFetcherUnitTest {

    private static MockHttpURLConnection mockHttpURLConnection;

    @BeforeAll
    public static void setUp() {
        mockHttpURLConnection = new MockHttpURLConnection(null);
        URL.setURLStreamHandlerFactory(new MockURLStreamHandlerFactory(mockHttpURLConnection));
    }

    @Test
    void givenMockedUrl_whenRequestSent_thenIsUrlAvailableTrue() throws Exception {
        mockHttpURLConnection.setResponseCode(HttpURLConnection.HTTP_OK);
        URL url = new URL("https://www.baeldung.com/");

        UrlFetcher fetcher = new UrlFetcher(url);
        assertTrue(fetcher.isUrlAvailable(), "Url should be available: ");
    }

    @Test
    void givenMockedUrl_whenRequestSent_thenIsUrlAvailableFalse() throws Exception {
        mockHttpURLConnection.setResponseCode(HttpURLConnection.HTTP_FORBIDDEN);
        URL url = new URL("https://www.baeldung.com/");

        UrlFetcher fetcher = new UrlFetcher(url);
        assertFalse(fetcher.isUrlAvailable(), "Url should NOT be available: ");
    }

}
