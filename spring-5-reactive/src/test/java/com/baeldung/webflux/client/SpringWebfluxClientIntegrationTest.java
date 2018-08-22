package com.baeldung.webflux.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.tomakehurst.wiremock.WireMockServer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringWebfluxClientIntegrationTest {

    private static final WireMockServer MOCK_SERVER = new WireMockServer(9001);
    private static final OutputCapture OUTPUT_CAPTURE = new OutputCapture();

    @BeforeClass
    public static void setUp() {
        MOCK_SERVER.start();
        MOCK_SERVER.stubFor(get(urlEqualTo("/time")).willReturn(aResponse().withHeader(CONTENT_TYPE, TEXT_EVENT_STREAM_VALUE)
            .withBody("data: 150001\n\ndata: 150002\n\ndata: 150003")));
    }

    @AfterClass
    public static void tearDown() {
        MOCK_SERVER.shutdown();
    }

    @Test
    public void whenAppIsStarted_thenServerIsCalledOnceAndWeReceivedData() {
        MOCK_SERVER.verify(1, getRequestedFor(urlEqualTo("/time")));
        OUTPUT_CAPTURE.expect(containsString("WebfluxClient - Received 150001"));
    }
}