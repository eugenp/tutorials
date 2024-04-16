package com.baeldung.jersey.server.rest;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.Test;

import com.baeldung.jersey.server.config.ViewApplicationConfig;
import com.baeldung.jersey.server.model.Fruit;
import com.baeldung.jersey.server.providers.FruitExceptionMapper;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

class FruitResourceIntegrationTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        forceSet(TestProperties.CONTAINER_PORT, "0");

        ViewApplicationConfig config = new ViewApplicationConfig();
        config.register(FruitExceptionMapper.class);
        return config;
    }

    @Test
    void givenGetAllFruit_whenCorrectRequest_thenAllTemplateInvoked() {
        final String response = target("/fruit/all").request()
            .get(String.class);
        assertThat(response, allOf(containsString("banana"), containsString("apple"), containsString("kiwi")));
    }

    @Test
    void givenGetFruit_whenCorrectRequest_thenIndexTemplateInvoked() {
        final String response = target("/fruit").request()
            .get(String.class);
        assertThat(response, containsString("Welcome Fruit Index Page!"));
    }

    @Test
    void givenGetFruitByName_whenFruitUnknown_thenErrorTemplateInvoked() {
        final String response = target("/fruit/orange").request()
            .get(String.class);
        assertThat(response, containsString("Error -  Fruit not found: orange!"));
    }

    @Test
    void givenCreateFruit_whenFormContainsNullParam_thenResponseCodeIsBadRequest() {
        Form form = new Form();
        form.param("name", "apple");
        form.param("colour", null);
        Response response = target("fruit/create").request(MediaType.APPLICATION_FORM_URLENCODED)
            .post(Entity.form(form));

        assertEquals(400, response.getStatus());
        assertThat(response.readEntity(String.class), containsString("Fruit colour must not be null"));
    }
    
    @Test
    void givenCreateFruit_whenJsonIsCorrect_thenResponseCodeIsCreated() {
        Response response = target("fruit/created").request()
            .post(Entity.json("{\"name\":\"strawberry\",\"weight\":20}"));

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertThat(response.readEntity(String.class), containsString("Fruit saved : Fruit [name: strawberry colour: null]"));
    }

    @Test
    void givenUpdateFruit_whenFormContainsBadSerialParam_thenResponseCodeIsBadRequest() {
        Form form = new Form();
        form.param("serial", "2345-2345");

        Response response = target("fruit/update").request(MediaType.APPLICATION_FORM_URLENCODED)
            .put(Entity.form(form));

        assertEquals(400, response.getStatus());
        assertThat(response.readEntity(String.class), containsString("Fruit serial number is not valid"));
    }

    @Test
    void givenCreateFruit_whenFruitIsInvalid_thenResponseCodeIsBadRequest() {
        Fruit fruit = new Fruit("Blueberry", "purple");
        fruit.setWeight(1);

        Response response = target("fruit/create").request(MediaType.APPLICATION_JSON_TYPE)
            .post(Entity.entity(fruit, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(400, response.getStatus());
        assertThat(response.readEntity(String.class), containsString("Fruit weight must be 10 or greater"));
    }

    @Test
    void givenFruitExists_whenSearching_thenResponseContainsFruit() {
        Fruit fruit = new Fruit();
        fruit.setName("strawberry");
        fruit.setWeight(20);
        Response response = target("fruit/create").request(MediaType.APPLICATION_JSON_TYPE)
            .post(Entity.entity(fruit, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(204, response.getStatus());

        final String json = target("fruit/search/strawberry").request()
            .get(String.class);
        assertThat(json, containsString("{\"name\":\"strawberry\",\"weight\":20}"));
    }
    
    @Test
    void givenFruitExists_whenSearching_thenResponseContainsFruitEntity() {
        Fruit fruit = new Fruit();
        fruit.setName("strawberry");
        fruit.setWeight(20);
        Response response = target("fruit/create").request(MediaType.APPLICATION_JSON_TYPE)
            .post(Entity.entity(fruit, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(204, response.getStatus());

        final Fruit entity = target("fruit/search/strawberry").request()
            .get(Fruit.class);

        assertEquals("strawberry", entity.getName());
        assertEquals(Integer.valueOf(20), entity.getWeight());
    }

    @Test
    void givenFruit_whenFruitIsInvalid_thenReponseContainsCustomExceptions() {
        final Response response = target("fruit/exception").request()
            .get();

        assertEquals(400, response.getStatus());
        String responseString = response.readEntity(String.class);
        assertThat(responseString, containsString("exception.<return value>.colour size must be between 5 and 200"));
        assertThat(responseString, containsString("exception.<return value>.name size must be between 5 and 200"));
    }

}
