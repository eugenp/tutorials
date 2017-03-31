package com.baeldung.jetty;


import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class JettyTest {
    private JettyServer jettyServer;

    @Before
    public void setup() throws Exception {
        jettyServer = new JettyServer();
        jettyServer.start();
    }

    @After
    public void cleanup() throws Exception {
        Thread.sleep(2000);
        jettyServer.stop();
    }

    @Test
    public void givenServer_whenSendRequestToBlockingServlet_thenReturnStatusOK() throws Exception {
        //given
        String url = "http://localhost:8090/status";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        //then
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);

    }

    @Test
    public void givenServer_whenSendRequestToNonBlockingServlet_thenReturnStatusOK() throws Exception {
        //when
        String url = "http://localhost:8090/heavy/async";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        //then
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
        String responseContent = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        assertThat(responseContent).isEqualTo("This is some heavy resource that will be served in an async way");
    }
}
