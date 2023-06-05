package com.baeldung.mock.url;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;

public class UrlFetcherUnitTest {

    private static MockHttpURLConnection mockHttpURLConnection;

    @BeforeClass
    public static void setUp() {
        mockHttpURLConnection = new MockHttpURLConnection(null);
        URL.setURLStreamHandlerFactory(new MockURLStreamHandlerFactory(mockHttpURLConnection));
    }

    @Test
    public void givenMockedUrl_whenRequestSent_thenIsUrlAvailableTrue() throws Exception {
        mockHttpURLConnection.setResponseCode(HttpURLConnection.HTTP_OK);
        URL url = new URL("https://www.baeldung.com/");

        UrlFetcher fetcher = new UrlFetcher(url);
        assertTrue("Url should be available: ", fetcher.isUrlAvailable());
    }

    @Test
    public void givenMockedUrl_whenRequestSent_thenIsUrlAvailableFalse() throws Exception {
        mockHttpURLConnection.setResponseCode(HttpURLConnection.HTTP_FORBIDDEN);
        URL url = new URL("https://www.baeldung.com/");

        UrlFetcher fetcher = new UrlFetcher(url);
        assertFalse("Url should NOT be available: ", fetcher.isUrlAvailable());
    }

}
