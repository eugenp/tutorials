package org.baeldung.httpclient;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.junit.After;
import org.junit.Test;

public class HttpClientTimeoutLiveTest {

    private CloseableHttpResponse response;

    @After
    public final void after() throws IllegalStateException, IOException {
        if (response == null) {
            return;
        }

        try {
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                final InputStream instream = entity.getContent();
                instream.close();
            }
        } finally {
            response.close();
        }
    }

    // tests

    @Test
    public final void givenUsingDeprecatedApi_whenSettingTimeoutViaRawParams_thenCorrect() throws ClientProtocolException, IOException {
        final int timeout = 2;
        final DefaultHttpClient client = new DefaultHttpClient();
        final HttpGet request = new HttpGet("http://www.github.com");

        final HttpParams httpParams = client.getParams();
        httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
        httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);
        // httpParams.setLongParameter(ClientPNames.CONN_MANAGER_TIMEOUT, new Long(timeout * 1000)); // https://issues.apache.org/jira/browse/HTTPCLIENT-1418

        response = client.execute(request);

        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

    @Test
    public final void givenUsingDeprecatedApi_whenSettingTimeoutViaHigherLevelApi_thenCorrect() throws ClientProtocolException, IOException {
        final int timeout = 2;
        final DefaultHttpClient client = new DefaultHttpClient();
        final HttpGet request = new HttpGet("http://www.github.com");

        final HttpParams httpParams = client.getParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, timeout * 1000); // http.connection.timeout
        HttpConnectionParams.setSoTimeout(httpParams, timeout * 1000); // http.socket.timeout

        response = client.execute(request);

        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();
    }

    @Test
    public final void givenUsingNewApi_whenSettingTimeoutViaHighLevelApi_thenCorrect() throws ClientProtocolException, IOException {
        final int timeout = 5;

        final RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
        final CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

        final HttpGet request = new HttpGet("http://www.github.com");

        response = client.execute(request);

        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    /**
     * This simulates a timeout against a domain with multiple routes/IPs to it (not a single raw IP)
     */
    @Test(expected = ConnectTimeoutException.class)
    public final void givenTimeoutIsConfigured_whenTimingOut_thenTimeoutException() throws ClientProtocolException, IOException {
        final int timeout = 3;

        final RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
        final CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

        final HttpGet request = new HttpGet("http://www.google.com:81");
        client.execute(request);
    }

}
