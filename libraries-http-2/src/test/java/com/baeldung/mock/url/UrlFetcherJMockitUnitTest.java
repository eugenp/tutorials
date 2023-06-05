package com.baeldung.mock.url;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;


@RunWith(JMockit.class)
public class UrlFetcherJMockitUnitTest {

    @Test
    public void givenMockedUrl_whenRequestSent_thenIsUrlAvailableTrue(@Mocked URL anyURL, @Mocked HttpURLConnection mockConn) throws Exception {
        new Expectations() {{
                mockConn.getResponseCode();
                result = HttpURLConnection.HTTP_OK;
        }};

        UrlFetcher fetcher = new UrlFetcher(new URL("https://www.baeldung.com/"));
        assertTrue("Url should be available: ", fetcher.isUrlAvailable());
    }
    
    @Test
    public void givenMockedUrl_whenRequestSent_thenIsUrlAvailableFalse(@Mocked URL anyURL, @Mocked HttpURLConnection mockConn) throws Exception {
        new Expectations() {{
                mockConn.getResponseCode();
                result = HttpURLConnection.HTTP_INTERNAL_ERROR;
        }};

        UrlFetcher fetcher = new UrlFetcher(new URL("https://www.baeldung.com/"));
        assertFalse("Url should NOT be available: ", fetcher.isUrlAvailable());
    }

}
