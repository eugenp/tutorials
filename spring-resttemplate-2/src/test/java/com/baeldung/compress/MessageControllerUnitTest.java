package com.baeldung.compress;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageControllerUnitTest {

	private static final Logger LOG = LoggerFactory.getLogger(MessageControllerUnitTest.class);

	@Autowired
	private RestTemplate restTemplate;

	@LocalServerPort
	private int randomServerPort;

	/**
	 * As a further test you can intercept the request body, using a tool like
	 * Wireshark, to see the request body is actually gzipped.
	 *
	 * @throws Exception
	 */
	@Test
	public void givenRestTemplate_whenPostCompressedRequest_thenRespondsSuccessfully() throws Exception {

		final String text = "Hello Baeldung!";
		Message message = new Message(text);

		HttpEntity<Message> request = new HttpEntity<>(message);
		String uri = String.format("http://localhost:%s/%s", randomServerPort, MessageController.REQUEST_MAPPING);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, request, String.class);

		String response = responseEntity.getBody();
		LOG.info("Got response [{}]", response);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(response);
		assertEquals(MessageController.PROCESSED + text, response);
	}

}
