package com.baeldung.httpclient;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

public class HttpClientTimeoutLiveTest {

    private CloseableHttpResponse response;

    @After
    public final void after() throws IllegalStateException, IOException {
        ResponseUtil.closeResponse(response);
    }

    // tests
    @Test
    public final void givenUsingOldApi_whenSettingTimeoutViaParameter_thenCorrect() throws IOException {
        
        DefaultHttpClient httpClient = new DefaultHttpClient();
        int timeout = 5; // seconds
        HttpParams httpParams = httpClient.getParams();
        httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
        httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);
        httpParams.setParameter(ClientPNames.CONN_MANAGER_TIMEOUT, new Long(timeout * 1000));
        
        final HttpGet request = new HttpGet("http://www.github.com");
        HttpResponse execute = httpClient.execute(request);
        assertThat(execute.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    public final void givenUsingNewApi_whenSettingTimeoutViaRequestConfig_thenCorrect() throws IOException {
        final int timeout = 2;
        final RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
        final CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        final HttpGet request = new HttpGet("http://www.github.com");

        response = client.execute(request);

        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    public final void givenUsingNewApi_whenSettingTimeoutViaSocketConfig_thenCorrect() throws IOException {
        final int timeout = 2;

        final SocketConfig config = SocketConfig.custom().setSoTimeout(timeout * 1000).build();
        final CloseableHttpClient client = HttpClientBuilder.create().setDefaultSocketConfig(config).build();

        final HttpGet request = new HttpGet("http://www.github.com");

        response = client.execute(request);

        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    public final void givenUsingNewApi_whenSettingTimeoutViaHighLevelApi_thenCorrect() throws IOException {
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
    @Ignore
    public final void givenTimeoutIsConfigured_whenTimingOut_thenTimeoutException() throws IOException {
        final int timeout = 3;

        final RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
        final CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

        final HttpGet request = new HttpGet("http://www.google.com:81");
        client.execute(request);
    }
    
    @Test
    public void whenSecuredRestApiIsConsumed_then200OK() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        int timeout = 20; // seconds
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout * 1000)
          .setConnectTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
        HttpGet getMethod = new HttpGet("http://localhost:8082/httpclient-simple/api/bars/1");
        getMethod.setConfig(requestConfig);

        int hardTimeout = 5; // seconds
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getMethod.abort();
            }
        };
        new Timer(true).schedule(task, hardTimeout * 1000);

        HttpResponse response = httpClient.execute(getMethod);
        System.out.println("HTTP Status of response: " + response.getStatusLine().getStatusCode());
    }
    
}
