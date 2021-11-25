package com.baeldung.mime;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebConfig.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ComponentScan({"com.baeldung.mime"})
@EnableAutoConfiguration
@ActiveProfiles("test")
public class FooLiveTest {
	
	@LocalServerPort
	private int port;

    @Autowired
    protected IMarshaller marshaller;

    // API

    public final void create() {
        create(new Foo(randomAlphabetic(6)));
    }

    public final String createAsUri() {
        return createAsUri(new Foo(randomAlphabetic(6)));
    }

    protected final void create(final Foo resource) {
        createAsUri(resource);
    }

    private final String createAsUri(final Foo resource) {
        final Response response = createAsResponse(resource);
        return getURL() + "/" + response.getBody().as(Foo.class).getId();
    }

    private final Response createAsResponse(final Foo resource) {

        final String resourceAsString = marshaller.encode(resource);
        return RestAssured.given()
            .contentType(marshaller.getMime())
            .body(resourceAsString)
            .post(getURL());
    }

    //

    protected String getURL() {
        return "http://localhost:" + port + "/foos";
    }
    
    @Test
	public void givenResourceExists_whenRetrievingResource_thenEtagIsAlsoReturned() {
		// Given
		final String uriOfResource = createAsUri();

		// When
		final Response findOneResponse = RestAssured.given().header("Accept", "application/json").get(uriOfResource);

		// Then
		assertEquals(findOneResponse.getStatusCode(), 200);
	}

}
