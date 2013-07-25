package org.baeldung.client;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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

        final int timeout = 5; // seconds
        final HttpParams httpParams = httpClient.getParams();
        // - note: timeout via raw String parameters
        // httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
        // httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);
        // httpParams.setParameter("http.connection-manager.timeout", new Long(timeout * 1000));
        // httpParams.setParameter("http.protocol.head-body-timeout", timeout * 1000);

        // - note: timeout via the API
        HttpConnectionParams.setConnectionTimeout(httpParams, timeout * 1000); // http.connection.timeout
        HttpConnectionParams.setSoTimeout(httpParams, timeout * 1000); // http.socket.timeout

        final HttpResponse response = httpClient.execute(new HttpGet("http://localhost:8080/spring-security-rest-template/api/foos/1/unsecured"));
        final int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
    }

}
