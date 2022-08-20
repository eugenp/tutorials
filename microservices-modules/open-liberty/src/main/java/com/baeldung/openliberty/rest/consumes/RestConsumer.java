package com.baeldung.openliberty.rest.consumes;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

public class RestConsumer {

    public static String consumeWithJsonb(String targetUrl) {
        Client client = ClientBuilder.newClient();
        Response response = client.target(targetUrl).request().get();
        String result = response.readEntity(String.class);
        response.close();
        client.close();
        return result;
    }

}
