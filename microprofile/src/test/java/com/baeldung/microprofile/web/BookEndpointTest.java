package com.baeldung.microprofile.web;

//import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;
import org.junit.*;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BookEndpointTest {

    private static final String url = "http://localhost:8080/library/api/books/";
    private Client client;

    @Before
    public void setUp() throws Exception {
        client = ClientBuilder.newClient();
        //client.register(JsrJsonpProvider.class);
    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }

    @Test
    public void testAll() {
        //deleteAll();
        //add();
        //getAllBooks();
    }

    public void deleteAll() {
    }

    public void add() {
        JsonObject book = Json.createObjectBuilder()
                .add("name","Spring in action")
                .build();
        Response response = client.target(url)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(book,MediaType.APPLICATION_JSON_TYPE));
        System.out.println(response);

    }

    public void getBook() {
    }

    public void getAllBooks() {
        JsonArray book = client.target(url)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(JsonArray.class);
        System.out.println(book);
    }
}
