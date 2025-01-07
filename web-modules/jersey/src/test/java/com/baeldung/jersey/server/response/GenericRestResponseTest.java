
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import jakarta.ws.rs.core.MediaType;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;


public class GenericRestResponseTest {

    private WireMockServer wireMockServer;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setup() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8089);
    }

    @After
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void testSuccessfulResponse() {
        stubFor(post(urlEqualTo("/data"))
            .withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON))
            .withRequestBody(equalToJson("{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", MediaType.APPLICATION_JSON)
                .withBody("{\"status\":\"success\"}")));

        System.setProperty("api.base.url", "http://localhost:8089");

        GenericRestResponse.main(new String[]{});

        assertThat(outContent.toString().trim())
            .contains("Response Body: {\"status\":\"success\"}");
    }

    @Test
    public void testFailedResponse() {
        stubFor(post(urlEqualTo("/data"))
            .willReturn(aResponse()
                .withStatus(500)));

        System.setProperty("api.base.url", "http://localhost:8089");

        GenericRestResponse.main(new String[]{});

        assertThat(errContent.toString().trim())
            .contains("Failed to get a successful response");
    }

    @Test
    public void testResourceCleanup() {
        stubFor(post(urlEqualTo("/data"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBody("{}")));

        GenericRestResponse.main(new String[]{});

        verify(1, postRequestedFor(urlEqualTo("/data")));
    }
}