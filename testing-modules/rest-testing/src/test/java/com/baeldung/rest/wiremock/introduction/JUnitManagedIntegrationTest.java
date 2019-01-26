package com.baeldung.rest.wiremock.introduction;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.junit.Assert.assertEquals;

public class JUnitManagedIntegrationTest {

    private static final String BAELDUNG_WIREMOCK_PATH = "/baeldung/wiremock";
    private static final String APPLICATION_JSON = "application/json";

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void givenJUnitManagedServer_whenMatchingURL_thenCorrect() throws IOException {
        stubFor(get(urlPathMatching("/baeldung/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", APPLICATION_JSON)
                        .withBody("\"testing-library\": \"WireMock\"")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:8080/baeldung/wiremock");
        HttpResponse httpResponse = httpClient.execute(request);
        String stringResponse = convertHttpResponseToString(httpResponse);

        verify(getRequestedFor(urlEqualTo(BAELDUNG_WIREMOCK_PATH)));
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
        assertEquals(APPLICATION_JSON, httpResponse.getFirstHeader("Content-Type").getValue());
        assertEquals("\"testing-library\": \"WireMock\"", stringResponse);
    }

    @Test
    public void givenJUnitManagedServer_whenMatchingHeaders_thenCorrect() throws IOException {
        stubFor(get(urlPathEqualTo(BAELDUNG_WIREMOCK_PATH))
                .withHeader("Accept", matching("text/.*"))
                .willReturn(aResponse()
                        .withStatus(503)
                        .withHeader("Content-Type", "text/html")
                        .withBody("!!! Service Unavailable !!!")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:8080/baeldung/wiremock");
        request.addHeader("Accept", "text/html");
        HttpResponse httpResponse = httpClient.execute(request);
        String stringResponse = convertHttpResponseToString(httpResponse);

        verify(getRequestedFor(urlEqualTo(BAELDUNG_WIREMOCK_PATH)));
        assertEquals(503, httpResponse.getStatusLine().getStatusCode());
        assertEquals("text/html", httpResponse.getFirstHeader("Content-Type").getValue());
        assertEquals("!!! Service Unavailable !!!", stringResponse);
    }

    @Test
    public void givenJUnitManagedServer_whenMatchingBody_thenCorrect() throws IOException {
        stubFor(post(urlEqualTo(BAELDUNG_WIREMOCK_PATH))
                .withHeader("Content-Type", equalTo(APPLICATION_JSON))
                .withRequestBody(containing("\"testing-library\": \"WireMock\""))
                .withRequestBody(containing("\"creator\": \"Tom Akehurst\""))
                .withRequestBody(containing("\"website\": \"wiremock.org\""))
                .willReturn(aResponse().withStatus(200)));

        InputStream jsonInputStream = this.getClass().getClassLoader().getResourceAsStream("wiremock_intro.json");
        String jsonString = convertInputStreamToString(jsonInputStream);
        StringEntity entity = new StringEntity(jsonString);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://localhost:8080/baeldung/wiremock");
        request.addHeader("Content-Type", APPLICATION_JSON);
        request.setEntity(entity);
        HttpResponse response = httpClient.execute(request);

        verify(postRequestedFor(urlEqualTo(BAELDUNG_WIREMOCK_PATH))
                .withHeader("Content-Type", equalTo(APPLICATION_JSON)));
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void givenJUnitManagedServer_whenNotUsingPriority_thenCorrect() throws IOException {
        stubFor(get(urlPathMatching("/baeldung/.*")).willReturn(aResponse().withStatus(200)));
        stubFor(get(urlPathEqualTo(BAELDUNG_WIREMOCK_PATH)).withHeader("Accept", matching("text/.*")).willReturn(aResponse().withStatus(503)));

        HttpResponse httpResponse = generateClientAndReceiveResponseForPriorityTests();

        verify(getRequestedFor(urlEqualTo(BAELDUNG_WIREMOCK_PATH)));
        assertEquals(503, httpResponse.getStatusLine().getStatusCode());
    }

    @Test
    public void givenJUnitManagedServer_whenUsingPriority_thenCorrect() throws IOException {
        stubFor(get(urlPathMatching("/baeldung/.*")).atPriority(1).willReturn(aResponse().withStatus(200)));
        stubFor(get(urlPathEqualTo(BAELDUNG_WIREMOCK_PATH)).atPriority(2).withHeader("Accept", matching("text/.*")).willReturn(aResponse().withStatus(503)));

        HttpResponse httpResponse = generateClientAndReceiveResponseForPriorityTests();

        verify(getRequestedFor(urlEqualTo(BAELDUNG_WIREMOCK_PATH)));
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
    }

    private static String convertHttpResponseToString(HttpResponse httpResponse) throws IOException {
        InputStream inputStream = httpResponse.getEntity().getContent();
        return convertInputStreamToString(inputStream);
    }

    private static String convertInputStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        String string = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return string;
    }

    private HttpResponse generateClientAndReceiveResponseForPriorityTests() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:8080/baeldung/wiremock");
        request.addHeader("Accept", "text/xml");
        return httpClient.execute(request);
    }
}