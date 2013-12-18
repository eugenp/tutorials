package org.baeldung.client;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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
        final CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        final int timeout = 20; // seconds
        final RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).setSocketTimeout(timeout).build();
        final HttpGet getMethod = new HttpGet("http://localhost:8080/spring-security-rest-basic-auth/api/bars/1");
        getMethod.setConfig(requestConfig);

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

}
