package com.baeldung.blockingnonblocking;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertTrue;

public class BlockingClientUnitTest {
    private static final String REQUESTED_RESOURCE = "/test.json";

    @Rule public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());

    @Before
    public void setup() {
        stubFor(get(urlEqualTo(REQUESTED_RESOURCE)).willReturn(aResponse()
          .withStatus(200)
          .withBody("{ \"response\" : \"It worked!\" }\r\n\r\n")));
    }

    @Test
    public void givenJavaIOSocket_whenReadingAndWritingWithStreams_thenReadSuccessfully() throws IOException {
        // given an IO socket and somewhere to store our result
        Socket socket = new Socket("localhost", wireMockRule.port());
        StringBuilder ourStore = new StringBuilder();

        // when we write and read (using try-with-resources so our resources are auto-closed)
        try (InputStream serverInput = socket.getInputStream();
          BufferedReader reader = new BufferedReader(new InputStreamReader(serverInput));
          OutputStream clientOutput = socket.getOutputStream();
          PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientOutput))) {
            writer.print("GET " + REQUESTED_RESOURCE + " HTTP/1.0\r\n\r\n");
            writer.flush(); // important - without this the request is never sent, and the test will hang on readLine()

            for (String line; (line = reader.readLine()) != null; ) {
                ourStore.append(line);
                ourStore.append(System.lineSeparator());
            }
        }

        // then we read and saved our data
        assertTrue(ourStore
          .toString()
          .contains("It worked!"));
    }
}