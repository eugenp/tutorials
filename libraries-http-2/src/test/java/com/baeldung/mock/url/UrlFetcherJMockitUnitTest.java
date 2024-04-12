package com.baeldung.mock.url;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit5.JMockitExtension;


@ExtendWith(JMockitExtension.class)
class UrlFetcherJMockitUnitTest {

    @Test
    void givenMockedUrl_whenRequestSent_thenIsUrlAvailableTrue(@Mocked URL anyURL, @Mocked HttpURLConnection mockConn) throws Exception {
        new Expectations() {{
                mockConn.getResponseCode();
                result = HttpURLConnection.HTTP_OK;
        }};

        UrlFetcher fetcher = new UrlFetcher(new URL("https://www.baeldung.com/"));
        assertTrue(fetcher.isUrlAvailable(), "Url should be available: ");
    }
    
    @Test
    void givenMockedUrl_whenRequestSent_thenIsUrlAvailableFalse(@Mocked URL anyURL, @Mocked HttpURLConnection mockConn) throws Exception {
        new Expectations() {{
                mockConn.getResponseCode();
                result = HttpURLConnection.HTTP_INTERNAL_ERROR;
        }};

        UrlFetcher fetcher = new UrlFetcher(new URL("https://www.baeldung.com/"));
        assertFalse(fetcher.isUrlAvailable(), "Url should NOT be available: ");
    }

}
