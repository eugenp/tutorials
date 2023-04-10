package com.baeldung.httpclient.expandUrl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import com.google.common.base.Preconditions;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import org.apache.hc.client5.http.classic.methods.HttpHead;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;

import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpHeaders;

import org.junit.jupiter.api.Test;

class HttpClientExpandUrlLiveTest {

    @Test
    void givenShortenedOnce_whenUrlIsExpanded_thenCorrectResult() throws IOException {
        final String expectedResult = "https://www.baeldung.com/rest-versioning";
        final String actualResult = expandSingleLevel("http://bit.ly/3LScTri");
        assertThat(actualResult, equalTo(expectedResult));
    }

    @Test
    void givenShortenedMultiple_whenUrlIsExpanded_thenCorrectResult() throws IOException {
        final String expectedResult = "https://www.baeldung.com/rest-versioning";
        final String actualResult = expand("http://t.co/e4rDDbnzmk");
        assertThat(actualResult, equalTo(expectedResult));
    }

    private String expand(final String urlArg) throws IOException {
        String originalUrl = urlArg;
        String newUrl = expandSingleLevel(originalUrl);
        while (!originalUrl.equals(newUrl)) {
            originalUrl = newUrl;
            newUrl = expandSingleLevel(originalUrl);
        }

        return newUrl;
    }


    private Pair<Integer, String> expandSingleLevelSafe(final String url) throws IOException {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
            .disableRedirectHandling()
            .build()) {
            HttpHead request = new HttpHead(url);
            Pair<Integer, String> resp = httpClient.execute(request, response -> {
                final int statusCode = response.getCode();
                if (statusCode != 301 && statusCode != 302) {
                    return new ImmutablePair<>(statusCode, url);
                }
                final Header[] headers = response.getHeaders(HttpHeaders.LOCATION);
                Preconditions.checkState(headers.length == 1);
                final String newUrl = headers[0].getValue();

                return new ImmutablePair<>(statusCode, newUrl);
            });
            return resp;
        }
    }

    private String expandSingleLevel(final String url) throws IOException {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
            .disableRedirectHandling()
            .build()) {
            HttpHead request = new HttpHead(url);
            String expandedUrl = httpClient.execute(request, response -> {
                final int statusCode = response.getCode();
                if (statusCode != 301 && statusCode != 302) {
                    return url;
                }
                final Header[] headers = response.getHeaders(HttpHeaders.LOCATION);
                Preconditions.checkState(headers.length == 1);

                return headers[0].getValue();
            });
            return expandedUrl;
        }
    }

}
