package com.baeldung.httpclient;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Demonstrates establishment of new connections by HttpClient on exceptions during interactions with server.
 *
 * @author  Resat SABIQ
 * @see <a href="https://github.com/haqer1/java-http-client-demo">java-http-client-demo</a>
 */
public class JavaHttpClientExceptionHandlingUnitTest {
    private final static org.slf4j.Logger logger
            = org.slf4j.LoggerFactory.getLogger(JavaHttpClientExceptionHandlingUnitTest.class.getName());
    private final static String CONNECTION_RESET_BY_PEER_CONFIG_MSG = "WireMock pre-configured for RESET.";
    private final static String CONNECTION_RESET_BY_PEER_DONE_MSG = "Making assertions & exiting from RESET test.";
    private final static String LOG_FILEPATH = "logback-HttpConnection.log";
    private final static String NEW_HTTP_CONN_MSG_PATTERN
            = "o.e.jetty.server.HttpConnection - New HTTP Connection HttpConnection@";

    private final static byte READ_TIMEOUT_SECONDS = 30;
    private final static int READ_TIMEOUT_DELAY_MS = 60000;
    private final static String CONNECTION_READ_TIMEOUT_CONFIG_MSG = MessageFormat.format(
            "WireMock pre-configured for TIMEOUT ({0} seconds).", READ_TIMEOUT_SECONDS);
    private final static String CONNECTION_READ_TIMEOUT_DONE_MSG = "Making assertions & exiting from TIMEOUT test.";
    private final static long WIRE_MOCK_CONCURRENT_LOGGING_AVOIDANCE_DELAY = 4;

    private final WireMockConfiguration mockConfiguration = WireMockConfiguration.options().dynamicPort();
    private final WireMockServer mockServer = new WireMockServer(mockConfiguration);
    private HttpRequest getRequest;
    private final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(30)).build();
    private byte minimumConnectionsExpected;

    @BeforeAll
    public static void ensureSequentialWireMockTestExecution() throws InterruptedException {
        TimeUnit.SECONDS.sleep(4);
    }

    @BeforeEach
    public void setup() {
        mockServer.start();
        String firstUrl = "http://localhost:" + mockServer.port() + "/first";

        getRequest = HttpRequest
                .newBuilder()
                .uri(URI.create(firstUrl))
                .timeout(Duration.ofSeconds(READ_TIMEOUT_SECONDS))
                .version(HttpClient.Version.HTTP_1_1)
                .build();
    }

    private void stubFor200() {
        mockServer.stubFor(WireMock
                .get(WireMock.anyUrl())
                .willReturn(WireMock
                        .aResponse()
                        .withStatus(200)));
    }

    @AfterEach
    public void tearDown() {
        List<ServeEvent> firstWiremockAllServeEvents = mockServer.getAllServeEvents();
        List<String> urlList = firstWiremockAllServeEvents
                .stream()
                .map(event -> event
                        .getRequest()
                        .getAbsoluteUrl()).collect(Collectors.toUnmodifiableList());

        logger.debug("All URLs before supplementary assertion: {}", urlList);

        assertTrue(urlList.size() >= minimumConnectionsExpected
                , "Throws tests must result in connections established 2 times");

        mockServer.stop();
    }

    /**
     * Upon IOException on a request HttpClient makes a new connection to the server immediately to
     * replace the current one in the internal pool.
     */
    @Test
    public final void givenHttpClient_whenIOExceptionThrown_thenThePoolConnectionIsReplacedWithNewOneImmediately()
            throws IOException {
        minimumConnectionsExpected = 2;

        mockServer.stubFor(WireMock
                .get(WireMock.anyUrl())
                .willReturn(
                    WireMock.aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER)
                )
        );
        logger.debug(CONNECTION_RESET_BY_PEER_CONFIG_MSG);

        assertThrows(IOException.class, () -> client.send(getRequest, HttpResponse.BodyHandlers.ofString()));

        logger.debug(CONNECTION_RESET_BY_PEER_DONE_MSG);

        assertCountOfNewConnections(CONNECTION_RESET_BY_PEER_CONFIG_MSG, minimumConnectionsExpected
                , CONNECTION_RESET_BY_PEER_DONE_MSG);
    }

    private void assertCountOfNewConnections(String startMsg, byte countExpected, String endMessage) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(LOG_FILEPATH));
        String line;
        boolean resetPreconfigured = false, exiting = false;
        byte newConnections = 0;
        while (!exiting && (line = reader.readLine()) != null) {
            if (line.contains(startMsg))
                resetPreconfigured = true;
            else if (line.contains(NEW_HTTP_CONN_MSG_PATTERN) && resetPreconfigured)
                newConnections++;
            else if (line.contains(endMessage))
                exiting = true;
        }

        assertTrue (resetPreconfigured, "Must contain: " +startMsg);
        assertEquals(countExpected, newConnections
                , "Must contain twice: " + NEW_HTTP_CONN_MSG_PATTERN);
        assertTrue (exiting, "Must contain: " +endMessage);
    }

    /**
     * Upon HttpTimeoutException during reading a request HttpClient makes a new connection to the server upon new
     * request to replace the current one in the internal pool.
     */
    @Test
    public final void
            givenHttpClient_whenTimeoutExceptionThrown_thenThePoolConnectionIsReplacedWithNewOneOnNewRequest()
            throws IOException, InterruptedException {
        minimumConnectionsExpected = 2;

        mockServer.stubFor(WireMock
                .get(WireMock.anyUrl())
                .willReturn(WireMock.aResponse().withStatus(200).withFixedDelay(READ_TIMEOUT_DELAY_MS))
        );
        logger.debug(CONNECTION_READ_TIMEOUT_CONFIG_MSG);

        assertThrows(HttpTimeoutException.class, () ->
                client.send(getRequest, HttpResponse.BodyHandlers.ofString())
        );

        stubFor200();
        HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Status 200 expected without timeout");

        logger.debug(CONNECTION_READ_TIMEOUT_DONE_MSG);

        assertCountOfNewConnections(CONNECTION_READ_TIMEOUT_CONFIG_MSG, minimumConnectionsExpected
                , CONNECTION_READ_TIMEOUT_DONE_MSG);
    }
}
