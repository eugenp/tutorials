package kerberos.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Procedure to run this manual test:
 * <ol>
 * <li>Start {@code KerberosMiniKdc}</li>
 * <li>Start {@code KerberizedServerApp}</li>
 * <li>Run the test</li>
 * </ol>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleClientManualTest {

	@Autowired
	private SampleClient sampleClient;

	@Test
	public void givenKerberizedRestTemplate_whenServiceCall_thenSuccess() {
		assertEquals("data from kerberized server", sampleClient.getData());
	}

	@Test
	public void givenRestTemplate_whenServiceCall_thenFail() {
		sampleClient.setRestTemplate(new RestTemplate());
		assertThrows(RestClientException.class, sampleClient::getData);
	}
}