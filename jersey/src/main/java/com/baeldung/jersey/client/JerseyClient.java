package com.baeldung.jersey.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.baeldung.jersey.client.filter.RequestClientFilter;
import com.baeldung.jersey.client.filter.ResponseClientFilter;
import com.baeldung.jersey.client.interceptor.RequestClientWriterInterceptor;

public class JerseyClient {

    private static final String URI_GREETINGS = "http://localhost:8080/jersey/greetings";

    public static String getHelloGreeting() {
        return createClient().target(URI_GREETINGS)
            .request()
            .get(String.class);
    }

    public static String getHiGreeting() {
        return createClient().target(URI_GREETINGS + "/hi")
            .request()
            .get(String.class);
    }

    public static Response getCustomGreeting() {
        return createClient().target(URI_GREETINGS + "/custom")
            .request()
            .post(Entity.text("custom"));
    }

    private static Client createClient() {
        ClientConfig config = new ClientConfig();
        config.register(RequestClientFilter.class);
        config.register(ResponseClientFilter.class);
        config.register(RequestClientWriterInterceptor.class);

        return ClientBuilder.newClient(config);
    }

}
