package com.baeldung.httpclient.conn;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static java.net.URI.create;


public class HttpClientConnectionManagementUnitTest {

    WireMockConfiguration firstConfiguration = WireMockConfiguration
      .options()
      .dynamicPort();
    WireMockConfiguration secondConfiguration = WireMockConfiguration
      .options()
      .dynamicPort();
    WireMockServer firstServer = new WireMockServer(firstConfiguration);
    WireMockServer secondServer = new WireMockServer(secondConfiguration);
    private String firstUrl;
    private String secondUrl;

    private HttpClient client = HttpClient.newHttpClient();
    private HttpClient secondClient = HttpClient.newHttpClient();

    private HttpRequest getRequest;
    private HttpRequest secondGet;

    @Before
    public void setup() {
        firstServer.start();
        secondServer.start();

        // add some request matchers
        firstServer.stubFor(WireMock
          .get(WireMock.anyUrl())
          .willReturn(WireMock
            .aResponse()
            .withStatus(200)));
        secondServer.stubFor(WireMock
          .get(WireMock.anyUrl())
          .willReturn(WireMock
            .aResponse()
            .withStatus(200)));

        firstUrl = "http://localhost:" + firstServer.port() + "/first";
        secondUrl = "http://localhost:" + secondServer.port() + "/second";

        getRequest = HttpRequest
          .newBuilder()
          .uri(create(firstUrl))
          .version(HttpClient.Version.HTTP_1_1)
          .build();

        secondGet = HttpRequest
          .newBuilder()
          .uri(create(secondUrl))
          .version(HttpClient.Version.HTTP_1_1)
          .build();
    }

    @After
    public void tearDown() {
        // display all the requests that the WireMock servers handled
        List<ServeEvent> firstWiremockAllServeEvents = firstServer.getAllServeEvents();
        List<ServeEvent> secondWiremockAllServeEvents = secondServer.getAllServeEvents();
        firstWiremockAllServeEvents
          .stream()
          .map(event -> event
            .getRequest()
            .getAbsoluteUrl())
          .forEach(System.out::println);
        secondWiremockAllServeEvents
          .stream()
          .map(event -> event
            .getRequest()
            .getAbsoluteUrl())
          .forEach(System.out::println);

        // stop the WireMock servers
        firstServer.stop();
        secondServer.stop();
    }

    // Example 1. Use an HttpClient to connect to the same endpoint - reuses a connection from the internal pool
    @Test
    public final void givenAnHttpClient_whenTwoConnectionsToSameEndpointMadeSequentially_thenConnectionReused() throws InterruptedException, IOException {

        // given two requests to the same destination
        final HttpResponse<String> firstResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        final HttpResponse<String> secondResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        assert (firstResponse.statusCode() == 200) && (secondResponse.statusCode() == 200);
    }

    // Example 2. Use separate HttpClients to connect to the same endpoint - creates a connection per client
    @Test
    public final void givenTwoHttpClients_whenEachClientMakesConnectionSequentially_thenConnectionCreatedForEach() throws InterruptedException, IOException {

        // given requests from two different client to same destination
        final HttpResponse<String> firstResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        final HttpResponse<String> secondResponse = secondClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        assert (firstResponse.statusCode() == 200) && (secondResponse.statusCode() == 200);
    }

    // Example 3. Use an HttpClient to Connect to first, second, then first endpoint again.
    // New connections made each time when pool size is 1, or re-used when not restricted.
    // Make sure to set the JVM arg when running the test:
    // -Djdk.httpclient.connectionPoolSize=1
    @Test
    public final void givenAnHttpClientAndAPoolSizeOfOne_whenTwoConnectionsMadeBackToOriginal_thenFirstConnectionPurged() throws InterruptedException, IOException {

        // given 3 requests, two to the first server and one to the second server
        final HttpResponse<String> firstResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        final HttpResponse<String> secondResponse = client.send(secondGet, HttpResponse.BodyHandlers.ofString());
        final HttpResponse<String> thirdResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        assert ((firstResponse.statusCode() == 200) && (secondResponse.statusCode() == 200) && (thirdResponse.statusCode() == 200));
    }

    // Example 4. Use an HttpClient to connect, wait for connection keepalive to pass, then connect again. New connection made for both calls.
    // Make sure to set the JVM arg when running the test:
    // -Djdk.httpclient.keepalive.timeout=2
    @Test
    public final void givenAnHttpClientAndConnectionKeepAliveOfTwoSeconds_whenCallMadeAfterKeepaliveExpires_thenNewConnection() throws InterruptedException, IOException {

        // given 2 requests to the same destination with the second call made after the keepalive timeout has passed
        final HttpResponse<String> firstResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        Thread.sleep(3000); // exceeds 2 seconds configured by JVM arg
        final HttpResponse<String> secondResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        assert ((firstResponse.statusCode() == 200) && (secondResponse.statusCode() == 200));
    }

}

