package com.baeldung.client;

import java.io.IOException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import com.baeldung.GetRequestMockServer;
import com.baeldung.httpclient.ResponseUtil;

/*
 * NOTE : Need module spring-security-rest-basic-auth to be running
 */
class HttpClientSandboxLiveTest extends GetRequestMockServer {

    @Test
    final void givenGetRequestExecuted_whenAnalyzingTheResponse_thenCorrectStatusCode() throws IOException {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        final AuthScope authscp = new AuthScope("localhost", 8080);
        credentialsProvider.setCredentials(authscp, new UsernamePasswordCredentials("user1", "user1Pass"));

        final CloseableHttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();

        final HttpGet httpGet = new HttpGet("http://localhost:" + serverPort + "/spring-security-rest-basic-auth/api/foos/1");
        final CloseableHttpResponse response = client.execute(httpGet);

        System.out.println(response.getStatusLine());

        ResponseUtil.closeResponse(response);
    }
}
