package karate;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(Karate.class)
@CucumberOptions(features = "classpath:karate/xml2-integration-test.feature")
public class Xml2IntegrationTest {
    
    @ClassRule
    public static WireMockClassRule WIREMOCK_RULE = new WireMockClassRule(wireMockConfig().dynamicPort());

    @Rule
    public WireMockClassRule instanceRule = WIREMOCK_RULE;
    
	private static final String URL = "/teachers";
	private static final String RESPONSE = Util.read("teachers.xml");

    @BeforeClass
    public static void before() {
        System.setProperty("wiremock.port", WIREMOCK_RULE.port() + "");
		stubFor(get(urlEqualTo(URL)).willReturn(
				aResponse().withStatus(200)
						.withHeader("Content-Type", "application/xml")
						.withBody(RESPONSE)));        
    }
    
}
