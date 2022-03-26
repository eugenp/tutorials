package com.baeldung.httpclient.httpclient;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ApacheHttpClientUnitTest extends GetRequestMockServer {

    @Test
    void givenDeveloperUsedHttpClientInterface_whenCreatingHttpClient_thenStatusIsOkButSonarReportsAnIssue() throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(serviceUrl);
        HttpResponse response = httpClient.execute(httpGet);

        assertThat(response.getCode()).isEqualTo(HttpStatus.SC_OK);
    }

}
