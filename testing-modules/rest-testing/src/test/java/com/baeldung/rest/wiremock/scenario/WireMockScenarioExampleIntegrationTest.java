package com.baeldung.rest.wiremock.scenario;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.stubbing.Scenario;

public class WireMockScenarioExampleIntegrationTest {
    
    private static final String THIRD_STATE = "third";
    private static final String SECOND_STATE = "second";
    private static final String TIP_01 = "finally block is not called when System.exit() is called in the try block";
    private static final String TIP_02 = "keep your code clean";
    private static final String TIP_03 = "use composition rather than inheritance";
    private static final String TEXT_PLAIN = "text/plain";
    
    static int port = 9999;
    
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(port);
    
    @Test
    public void changeStateOnEachCallTest() throws IOException {
        createWireMockStub(Scenario.STARTED, SECOND_STATE, TIP_01);
        createWireMockStub(SECOND_STATE, THIRD_STATE, TIP_02);
        createWireMockStub(THIRD_STATE, Scenario.STARTED, TIP_03);
        
        assertEquals(TIP_01, nextTip());
        assertEquals(TIP_02, nextTip());
        assertEquals(TIP_03, nextTip());
        assertEquals(TIP_01, nextTip());
    }

    private void createWireMockStub(String currentState, String nextState, String responseBody) {
        stubFor(get(urlEqualTo("/java-tip"))
            .inScenario("java tips")
            .whenScenarioStateIs(currentState)
            .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", TEXT_PLAIN)
                    .withBody(responseBody))
            .willSetStateTo(nextState)
            );
    }
    
    private String nextTip() throws ClientProtocolException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(String.format("http://localhost:%s/java-tip", port));
        HttpResponse httpResponse = httpClient.execute(request);
        return firstLineOfResponse(httpResponse);
    }

    private static String firstLineOfResponse(HttpResponse httpResponse) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))) {
            return reader.readLine();
        }
    }
    
}
