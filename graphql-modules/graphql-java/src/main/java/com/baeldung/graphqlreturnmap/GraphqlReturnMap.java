package com.baeldung.graphqlreturnmap;

import ratpack.server.RatpackServer;

public class GraphqlReturnMap {

    public static void main(String[] args) throws Exception {
        final RatpackServer server = RatpackServer.of(s -> s.handlers(chain -> chain.post("product", new AppHandler())));
        server.start();
    }

}