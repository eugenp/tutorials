package org.baeldung.httpclient;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HttpClientSandboxLiveTest {

    private CloseableHttpClient client;

    private CloseableHttpResponse response;

    @Before
    public final void before() {
        client = HttpClientBuilder.create().build();
    }

    @After
    public final void after() throws IllegalStateException, IOException {
        if (response == null) {
            return;
        }

        try {
            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                // EntityUtils.consume(entity);
                final InputStream instream = entity.getContent();
                instream.close();
            }
        } finally {
            response.close();
        }
    }

    // tests

    // simple request - response

    @Test
    public final void givenGetRequestExecuted_whenAnalyzingTheResponse_thenCorrectStatusCode() throws ClientProtocolException, IOException {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        final AuthScope authscp = new AuthScope("api.calltrackingmetrics.com", 443);
        credentialsProvider.setCredentials(authscp, new UsernamePasswordCredentials("mykey", "mysecret"));
        final HttpClientContext localContext = HttpClientContext.create();
        localContext.setCredentialsProvider(credentialsProvider);
        final HttpGet httpGet = new HttpGet("https://api.calltrackingmetrics.com/api/v1/accounts/myaccout/calls.json");
        response = client.execute(httpGet);

        System.out.println(response.getStatusLine());
    }

}
