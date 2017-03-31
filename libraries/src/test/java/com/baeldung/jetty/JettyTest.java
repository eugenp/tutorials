package com.baeldung.jetty;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class JettyTest {

    @Test
    public void givenServer_whenStartJettyServer_thenReturnStatusOK() throws Exception {
        //given
        JettyServer jettyServer = new JettyServer();

        //when
        jettyServer.start();
        String url = "http://localhost:8090/status";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);

        //then
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
        jettyServer.stop();
    }
}
