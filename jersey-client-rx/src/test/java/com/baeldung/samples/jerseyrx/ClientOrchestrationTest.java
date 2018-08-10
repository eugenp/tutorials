package com.baeldung.samples.jerseyrx;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import java.util.LinkedList;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import static junit.framework.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 *
 * @author baeldung
 */
public class ClientOrchestrationTest {

    Client client = ClientBuilder.newClient();

    WebTarget userIdService = client.target("http://localhost:8080/serviceA/id");
    WebTarget nameService = client.target("http://localhost:8080/serviceA/{empId}/name");
    WebTarget hashService = client.target("http://localhost:8080/serviceA/{comboIDandName}/hash");

    LinkedList<Throwable> failures = new LinkedList<>();

    Logger logger = Logger.getLogger("ClientOrchestrator");

    ClientOrchestration orchestrator = new ClientOrchestration();

    String jsonIdList = "{\"empIds\":[1,2,3,4,5,6]}";

    String[] nameList = new String[]{"n/a", "Thor", "Hulk", "BlackWidow", "BlackPanther", "TheTick", "Hawkeye"};

    String[] hashResultList = new String[]{"roht1", "kluh2", "WodiwKcalb3", "RehtnapKclab4", "kciteht5", "eyekwah6"};

    @Rule
    public WireMockRule wireMockServer = new WireMockRule();

    @Before
    public void setup() {

        stubFor(get(urlEqualTo("/serviceA/id")).willReturn(aResponse().withBody(jsonIdList).withHeader("Content-Type", "application/json")));

        stubFor(get(urlEqualTo("/serviceA/1/name")).willReturn(aResponse().withBody(nameList[1])));
        stubFor(get(urlEqualTo("/serviceA/2/name")).willReturn(aResponse().withBody(nameList[2])));
        stubFor(get(urlEqualTo("/serviceA/3/name")).willReturn(aResponse().withBody(nameList[3])));
        stubFor(get(urlEqualTo("/serviceA/4/name")).willReturn(aResponse().withBody(nameList[4])));
        stubFor(get(urlEqualTo("/serviceA/5/name")).willReturn(aResponse().withBody(nameList[5])));
        stubFor(get(urlEqualTo("/serviceA/6/name")).willReturn(aResponse().withBody(nameList[6])));

        stubFor(get(urlEqualTo("/serviceA/Thor1/hash")).willReturn(aResponse().withBody(hashResultList[0])));
        stubFor(get(urlEqualTo("/serviceA/Hulk2/hash")).willReturn(aResponse().withBody(hashResultList[1])));
        stubFor(get(urlEqualTo("/serviceA/BlackWidow3/hash")).willReturn(aResponse().withBody(hashResultList[2])));
        stubFor(get(urlEqualTo("/serviceA/BlackPanther4/hash")).willReturn(aResponse().withBody(hashResultList[3])));
        stubFor(get(urlEqualTo("/serviceA/TheTick5/hash")).willReturn(aResponse().withBody(hashResultList[4])));
        stubFor(get(urlEqualTo("/serviceA/Hawkeye6/hash")).willReturn(aResponse().withBody(hashResultList[5])));

    }

    @Test
    public void hits() {

        orchestrator.callBackOrchestrate();
        orchestrator.rxOrchestrate();
        orchestrator.observableJavaOrchestrate();
        orchestrator.flowableJavaOrchestrate();

        assertTrue(orchestrator.failures.isEmpty());
    }

  
}
