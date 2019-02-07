package org.baeldung.httpclient.base;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Test;

public class HttpClientSandboxLiveTest {

    private CloseableHttpClient client;

    private CloseableHttpResponse response;

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
        final AuthScope authscp = new AuthScope("localhost", 8080);
        credentialsProvider.setCredentials(authscp, new UsernamePasswordCredentials("user1", "user1Pass"));

        client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();

        final HttpGet httpGet = new HttpGet("http://localhost:8080/spring-security-rest-basic-auth/api/foos/1");
        response = client.execute(httpGet);

        System.out.println(response.getStatusLine());
    }

}
