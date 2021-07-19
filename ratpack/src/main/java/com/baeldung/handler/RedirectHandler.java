package com.baeldung.handler;

import ratpack.exec.Promise;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.http.client.HttpClient;
import ratpack.http.client.ReceivedResponse;

import java.net.URI;

public class RedirectHandler implements Handler {
    @Override
    public void handle(Context ctx) throws Exception {
        HttpClient client = ctx.get(HttpClient.class);
        URI uri = URI.create("http://localhost:5050/employee/1");
        Promise<ReceivedResponse> responsePromise = client.get(uri);
        responsePromise.map(response -> response.getBody()
            .getText()
            .toUpperCase())
            .then(responseText -> ctx.getResponse()
                .send(responseText));
    }
}