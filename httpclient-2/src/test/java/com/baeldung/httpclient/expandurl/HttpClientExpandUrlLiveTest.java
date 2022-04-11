package com.baeldung.httpclient.expandurl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class HttpClientExpandUrlLiveTest {

    private CloseableHttpClient client;

    @Before
    public final void before() {
        client = HttpClientBuilder.create().disableRedirectHandling().build();
    }

    @Test
    public final void givenShortenedOnce_whenUrlIsExpanded_thenCorrectResult() throws IOException {
        final String expectedResult = "http://www.baeldung.com/rest-versioning";
        final String actualResult = expandSingleLevel("http://bit.ly/13jEoS1");
        assertThat(actualResult, equalTo(expectedResult));
    }

    @Test
    public final void givenShortenedMultiple_whenUrlIsExpanded_thenCorrectResult() throws IOException {
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

    final String expandSafe(final String urlArg) throws IOException {
        String originalUrl = urlArg;
        String newUrl = expandSingleLevelSafe(originalUrl).getRight();
        final List<String> alreadyVisited = Lists.newArrayList(originalUrl, newUrl);
        while (!originalUrl.equals(newUrl)) {
            originalUrl = newUrl;
            final Pair<Integer, String> statusAndUrl = expandSingleLevelSafe(originalUrl);
            newUrl = statusAndUrl.getRight();
            final boolean isRedirect = statusAndUrl.getLeft() == 301 || statusAndUrl.getLeft() == 302;
            if (isRedirect && alreadyVisited.contains(newUrl)) {
                throw new IllegalStateException("Likely a redirect loop");
            }
            alreadyVisited.add(newUrl);
        }

        return newUrl;
    }

    private Pair<Integer, String> expandSingleLevelSafe(final String url) throws IOException {
        HttpHead request = null;
        HttpEntity httpEntity = null;
        InputStream entityContentStream = null;

        try {
            request = new HttpHead(url);
            final HttpResponse httpResponse = client.execute(request);

            httpEntity = httpResponse.getEntity();
            entityContentStream = httpEntity.getContent();

            final int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != 301 && statusCode != 302) {
                return new ImmutablePair<>(statusCode, url);
            }
            final Header[] headers = httpResponse.getHeaders(HttpHeaders.LOCATION);
            Preconditions.checkState(headers.length == 1);
            final String newUrl = headers[0].getValue();

            return new ImmutablePair<>(statusCode, newUrl);
        } catch (final IllegalArgumentException uriEx) {
            return new ImmutablePair<>(500, url);
        } finally {
            if (request != null) {
                request.releaseConnection();
            }
            if (entityContentStream != null) {
                entityContentStream.close();
            }
            if (httpEntity != null) {
                EntityUtils.consume(httpEntity);
            }
        }
    }

    private String expandSingleLevel(final String url) throws IOException {
        HttpHead request = null;

        try {
            request = new HttpHead(url);
            final HttpResponse httpResponse = client.execute(request);

            final int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != 301 && statusCode != 302) {
                return url;
            }
            final Header[] headers = httpResponse.getHeaders(HttpHeaders.LOCATION);
            Preconditions.checkState(headers.length == 1);

            return headers[0].getValue();
        } catch (final IllegalArgumentException uriEx) {
            return url;
        } finally {
            if (request != null) {
                request.releaseConnection();
            }
        }
    }

}
