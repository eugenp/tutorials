package com.baeldung.jersey.client.listdemo;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

public class JerseyListDemoUnitTest  extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ListDemoApp();
    }

    @Test
    public void givenList_whenUsingQueryParam_thenPassParamsAsList() {
        Response response = target("/")
          .queryParam("items", "item1", "item2")
          .request()
          .get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Received items: [item1, item2]", response.readEntity(String.class));
    }

    @Test
    public void givenList_whenUsingCommaSeparatedString_thenPassParamsAsList() {
        Response response = target("/")
          .queryParam("items", "item1,item2")
          .request()
          .get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Received items: [item1,item2]", response.readEntity(String.class));
    }

    @Test
    public void givenList_whenUsingUriBuilder_thenPassParamsAsList() {
        List<String> itemsList = Arrays.asList("item1", "item2");
        UriBuilder builder = UriBuilder.fromUri("/");
        for (String item : itemsList) {
            builder.queryParam("items", item);
        }
        URI uri = builder.build();
        String expectedUri = "/?items=item1&items=item2";
        assertEquals(expectedUri, uri.toString());
    }
}
