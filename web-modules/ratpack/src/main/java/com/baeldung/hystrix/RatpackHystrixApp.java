package com.baeldung.hystrix;

import ratpack.guice.Guice;
import ratpack.http.client.HttpClient;
import ratpack.hystrix.HystrixMetricsEventStreamHandler;
import ratpack.hystrix.HystrixModule;
import ratpack.server.RatpackServer;

import java.net.URI;

public class RatpackHystrixApp {

    public static void main(String[] args) throws Exception {
        final int timeout = Integer.valueOf(System.getProperty("ratpack.hystrix.timeout"));
        final URI eugenGithubProfileUri = new URI("https://api.github.com/users/eugenp");

        RatpackServer.start(server -> server
          .registry(Guice.registry(bindingsSpec -> bindingsSpec.module(new HystrixModule().sse())))
          .handlers(chain -> chain
            .get("rx", ctx -> new HystrixReactiveHttpCommand(ctx.get(HttpClient.class), eugenGithubProfileUri, timeout)
              .toObservable()
              .subscribe(ctx::render))
            .get("async", ctx -> ctx.render(new HystrixAsyncHttpCommand(eugenGithubProfileUri, timeout)
              .queue()
              .get()))
            .get("sync", ctx -> ctx.render(new HystrixSyncHttpCommand(eugenGithubProfileUri, timeout).execute()))
            .get("hystrix", new HystrixMetricsEventStreamHandler())));

    }
}
