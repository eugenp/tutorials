package kerberos.client;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
@FixMethodOrder
public class SampleServiceManualTest {

	@Autowired
	private SampleService sampleService;

	@Test
	public void a_givenKerberizedRestTemplate_whenServiceCall_thenSuccess() {
		assertNotNull(sampleService);
		assertEquals("data from kerberized server", sampleService.getData());
	}

	@Test
	public void b_givenRestTemplate_whenServiceCall_thenFail() {
		sampleService.setRestTemplate(new RestTemplate());
		assertThrows(RestClientException.class, sampleService::getData);
	}
}