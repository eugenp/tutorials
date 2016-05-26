package com.baeldung.rest.wiremock.introduction;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class JUnitManaged {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void givenJUnitManagedServer_whenStubbingURL_thenCorrect() throws IOException {
        configureFor("localhost", 8080);
        stubFor(get(urlPathMatching("/baeldung/.*")).willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody("\"testing-library\": \"WireMock\"")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:8080/baeldung/wiremock");
        HttpResponse httpResponse = httpClient.execute(request);
        String stringResponse = convertResponseToString(httpResponse);

        verify(getRequestedFor(urlEqualTo("/baeldung/wiremock")));
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
        assertEquals("application/json", httpResponse.getFirstHeader("Content-Type").getValue());
        assertEquals("\"testing-library\": \"WireMock\"", stringResponse);
    }

    private String convertResponseToString(HttpResponse response) throws IOException {
        InputStream responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String stringResponse = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return stringResponse;
    }
}
