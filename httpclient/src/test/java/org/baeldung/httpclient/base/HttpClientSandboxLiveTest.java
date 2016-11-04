package org.baeldung.httpclient.base;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

/*
 * NOTE : Need module spring-security-rest-basic-auth to be running
 */
public class HttpClientSandboxLiveTest {

    @Test
    public final void givenGetRequestExecuted_whenAnalyzingTheResponse_thenCorrectStatusCode() throws IOException {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        final AuthScope authscp = new AuthScope("localhost", 8080);
        credentialsProvider.setCredentials(authscp, new UsernamePasswordCredentials("user1", "user1Pass"));

        final CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();

        final HttpGet httpGet = new HttpGet("http://localhost:8080/spring-security-rest-basic-auth/api/foos/1");
        final CloseableHttpResponse response = client.execute(httpGet);

        System.out.println(response.getStatusLine());

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
}
