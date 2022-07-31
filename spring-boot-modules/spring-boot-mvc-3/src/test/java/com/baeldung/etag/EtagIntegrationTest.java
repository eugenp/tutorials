package com.baeldung.etag;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.util.Preconditions;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackageClasses = WebConfig.class)
public class EtagIntegrationTest {

	@LocalServerPort
	private int port;

	@Test
	public void givenResourceExists_whenRetrievingResource_thenEtagIsAlsoReturned() {
		// Given
		final String uriOfResource = createAsUri();

		// When
		final Response findOneResponse = RestAssured.given().header("Accept", "application/json").get(uriOfResource);

		// Then
		assertNotNull(findOneResponse.getHeader(HttpHeaders.ETAG));
	}

	@Test
	public void givenResourceWasRetrieved_whenRetrievingAgainWithEtag_thenNotModifiedReturned() {
		// Given
		final String uriOfResource = createAsUri();
		final Response findOneResponse = RestAssured.given().header("Accept", "application/json").get(uriOfResource);
		final String etagValue = findOneResponse.getHeader(HttpHeaders.ETAG);

		// When
		final Response secondFindOneResponse = RestAssured.given().header("Accept", "application/json")
				.headers("If-None-Match", etagValue).get(uriOfResource);

		// Then
		assertTrue(secondFindOneResponse.getStatusCode() == 304);
	}

	@Test
	public void givenResourceWasRetrievedThenModified_whenRetrievingAgainWithEtag_thenResourceIsReturned() {
		// Given
		final String uriOfResource = createAsUri();
		final Response firstFindOneResponse = RestAssured.given().header("Accept", "application/json")
				.get(uriOfResource);
		final String etagValue = firstFindOneResponse.getHeader(HttpHeaders.ETAG);
		final long createdId = firstFindOneResponse.jsonPath().getLong("id");

		Foo updatedFoo = new Foo("updated value");
		updatedFoo.setId(createdId);
		Response updatedResponse = RestAssured.given().contentType(ContentType.JSON).body(updatedFoo)
				.put(uriOfResource);
		assertThat(updatedResponse.getStatusCode() == 200);

		// When
		final Response secondFindOneResponse = RestAssured.given().header("Accept", "application/json")
				.headers("If-None-Match", etagValue).get(uriOfResource);

		// Then
		assertTrue(secondFindOneResponse.getStatusCode() == 200);
	}

	@Test
	@Ignore("Not Yet Implemented By Spring - https://jira.springsource.org/browse/SPR-10164")
	public void givenResourceExists_whenRetrievedWithIfMatchIncorrectEtag_then412IsReceived() {
		// Given
		final String uriOfResource = createAsUri();

		// When
		final Response findOneResponse = RestAssured.given().header("Accept", "application/json")
				.headers("If-Match", randomAlphabetic(8)).get(uriOfResource);

		// Then
		assertTrue(findOneResponse.getStatusCode() == 412);
	}

	private final String createAsUri() {
		final Response response = createAsResponse(new Foo(randomAlphabetic(6)));
		Preconditions.checkState(response.getStatusCode() == 201, "create operation: " + response.getStatusCode());

		return getURL() + "/" + response.getBody().as(Foo.class).getId();
	}

	private Response createAsResponse(final Foo resource) {
		String resourceAsString;
		try {
			resourceAsString = new ObjectMapper().writeValueAsString(resource);
		} catch (JsonProcessingException e) {
			throw new AssertionError("Error during serialization");
		}
		return RestAssured.given().contentType(MediaType.APPLICATION_JSON.toString()).body(resourceAsString)
				.post(getURL());
	}

	private String getURL() {
		return "http://localhost:" + port + "/foos";
	}

}