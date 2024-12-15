package com.baeldung.rest.wiremock.introduction;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JUnitManagedIntegrationTest {

    private static final String BAELDUNG_WIREMOCK_PATH = "/baeldung/wiremock";
    private static final String APPLICATION_JSON = "application/json";
    static int port;

    static {

        try {
            // Get a free port
            ServerSocket s = new ServerSocket(0);
            port = s.getLocalPort();
            s.close();

        } catch (IOException e) {
            // No OPS
        }
    }

    private WireMockServer wireMockServer;

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(port));
        wireMockServer.start();
        WireMock.configureFor("localhost", port);
    }

    @AfterEach
    void teardown() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    public void givenJUnitManagedServer_whenMatchingURL_thenCorrect() throws IOException {

        stubFor(get(urlPathMatching("/baeldung/.*"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", APPLICATION_JSON)
                .withBody("\"testing-library\": \"WireMock\"")));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(String.format("http://localhost:%s/baeldung/wiremock", port));
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
        HttpGet request = new HttpGet(String.format("http://localhost:%s/baeldung/wiremock", port));
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
        HttpPost request = new HttpPost(String.format("http://localhost:%s/baeldung/wiremock", port));
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
        HttpGet request = new HttpGet(String.format("http://localhost:%s/baeldung/wiremock", port));
        request.addHeader("Accept", "text/xml");
        return httpClient.execute(request);
    }
}