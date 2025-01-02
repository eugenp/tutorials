import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

package com.baeldung.jersey.server.response;




public class JsonResponseTest {

    private WireMockServer wireMockServer;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setup() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8089);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        System.setProperty("api.base.url", "http://localhost:8089");
    }

    @After
    public void teardown() {
        wireMockServer.stop();
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.clearProperty("api.base.url");
    }

    @Test
    public void whenUserExists_thenReturnUserName() {
        // Given
        stubFor(post(urlEqualTo("/user"))
            .withRequestBody(equalToJson("{\"id\":1}"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON)
                .withBody("{\"name\":\"John Smith\",\"id\":1}")));

        // When
        JsonResponse.main(new String[]{});

        // Then
        assertThat(outContent.toString().trim())
            .contains("User Name: John Smith");
    }

    @Test
    public void whenUserRequestFails_thenShowError() {
        // Given
        stubFor(post(urlEqualTo("/user"))
            .withRequestBody(equalToJson("{\"id\":1}"))
            .willReturn(aResponse()
                .withStatus(500)));

        // When
        JsonResponse.main(new String[]{});

        // Then
        assertThat(errContent.toString().trim())
            .contains("Failed to get user data");
        
        verify(1, postRequestedFor(urlEqualTo("/user")));
    }
}