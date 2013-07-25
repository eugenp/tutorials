package org.baeldung.client;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.baeldung.client.spring.ClientConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ClientConfig.class }, loader = AnnotationConfigContextLoader.class)
public class RawClientLiveTest {

    // tests

    @Test
    public final void whenSecuredRestApiIsConsumed_then200OK() throws ClientProtocolException, IOException {
        final DefaultHttpClient httpClient = new DefaultHttpClient();

        final int timeout = 20; // seconds
        final HttpParams httpParams = httpClient.getParams();
        configureViaRawApi(timeout, httpParams);
        // configureViaHighLevelApi(timeout, httpParams);

        final HttpGet getMethod = new HttpGet("http://localhost:8080/spring-security-rest-template/api/bars/1");

        final int hardTimeout = 5; // seconds
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (getMethod != null) {
                    getMethod.abort();
                }
            }
        };
        new Timer(true).schedule(task, hardTimeout * 1000);

        final HttpResponse response = httpClient.execute(getMethod);
        System.out.println("HTTP Status of response: " + response.getStatusLine().getStatusCode());
    }

    // util

    final void configureViaHighLevelApi(final int timeout, final HttpParams httpParams) {
        HttpConnectionParams.setConnectionTimeout(httpParams, timeout * 1000); // http.connection.timeout
        HttpConnectionParams.setSoTimeout(httpParams, timeout * 1000); // http.socket.timeout
        httpParams.setParameter(ClientPNames.CONN_MANAGER_TIMEOUT, new Long(timeout * 1000));
    }

    final void configureViaRawApi(final int timeout, final HttpParams httpParams) {
        httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
        httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);
        httpParams.setParameter(ClientPNames.CONN_MANAGER_TIMEOUT, new Long(timeout * 1000));
    }
}
