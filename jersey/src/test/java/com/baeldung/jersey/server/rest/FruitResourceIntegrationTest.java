package com.baeldung.jersey.server.rest;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import com.baeldung.jersey.server.config.ViewApplicationConfig;

public class FruitResourceIntegrationTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ViewApplicationConfig();
    }

    @Test
    public void testAllFruit() {
        final String response = target("/fruit/all").request()
            .get(String.class);
        Assert.assertThat(response, allOf(containsString("banana"), containsString("apple"), containsString("kiwi")));
    }

    @Test
    public void testIndex() {
        final String response = target("/fruit").request()
            .get(String.class);
        Assert.assertThat(response, containsString("Welcome Fruit Index Page!"));
    }

    @Test
    public void testErrorTemplate() {
        final String response = target("/fruit/orange").request()
            .get(String.class);
        Assert.assertThat(response, containsString("Error -  Fruit not found: orange!"));
    }

}
