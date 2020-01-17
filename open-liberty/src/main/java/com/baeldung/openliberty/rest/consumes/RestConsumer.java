package com.baeldung.openliberty.rest.consumes;

import java.net.URI;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

public class RestConsumer {

    public static String consumeWithJsonb(String targetUrl) {
        Client client = ClientBuilder.newClient();
        Response response = client.target(targetUrl).request().get();
        String result = response.readEntity(String.class);
        response.close();
        client.close();
        return result;
    }

    public static String consumeWithRestBuilder(String targetUrl) throws ProcessingException, UriNotFoundException {
        URI target = URI.create(targetUrl);;
        PersonClient person = RestClientBuilder.newBuilder()
            .baseUri(target)
            .register(UriNotFoundExceptionMapper.class)
            .build(PersonClient.class);
        return person.getPerson();
    }

}
